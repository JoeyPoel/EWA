<template>
  <div class="row detail justify-content-center  p-0 px-3 mt-1">
    <div class="col col-4 align-self-center">
      <div class="row justify-content-center">
        <img class="img" alt="Cabin image" :src="Warehouse.getImage()"/>
      </div>
      <div class="row">
        <form class="col p-1 p-xl-4">
          <h3>Warehouse {{ selectedWarehouse.id }}</h3>
          <p class="text-muted">Details</p>
          <div class="mt-1 mb-1 row">
            <div class="row">
              <label class="form-label" for="name">Name</label>
              <input class="form-control" id="name" type="text" v-model="this.copyOfWarehouse.name"/>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col">
              <label class="form-label" for="location">Location</label>
              <select class="form-control" id="location" v-model="this.copyOfWarehouse.location">
                <option v-for="location in Warehouse.locationList" :key="location">
                  {{ location }}
                </option>
              </select>
            </div>
          </div>
        </form>
      </div>
      <div class="row p-2 m-0 justify-content-center">
        <div class="col col-auto">
          <button class="btn btn-lg btn-secondary" @click="onReset()" :disabled="!hasChanged">
            Reset
          </button>
          <button class="btn btn-lg btn-secondary mx-1" @click="onClear()" :disabled="!hasChanged">
            Clear
          </button>
          <button class="btn btn-lg btn-danger" @click="onDelete()">
            Delete
          </button>
        </div>
        <div class="col col-auto mt-2">
          <button class="btn btn-lg btn-success mx-1" @click="onSave()" :disabled="!hasChanged">
            Save
          </button>
          <button class="btn btn-lg btn-secondary" @click="onCancel()" :disabled="!hasChanged">
            Cancel
          </button>
        </div>
      </div>
    </div>
    <div class="col col-8">
      <WarehouseDetailInventoryComponent :selected-warehouse="selectedWarehouse" :warehouses="warehouses"
      :products="products" :vendors="vendors" :transactions="transactions"/>
    </div>
  </div>
</template>

<script>
import {Warehouse} from "@/models/warehouse";
import WarehouseDetailInventoryComponent from "@/components/WarehouseDetailInventoryComponent.vue";

export default {
  name: "WarehouseDetailComponent",
  components: {WarehouseDetailInventoryComponent},
  computed: {
    selectedWarehouse() {
      return this.findSelectedWarehouseFromRoute()
    },
    hasChanged() {
      return !Warehouse.equals(this.selectedWarehouse, this.copyOfWarehouse)
    }
  },
  props: {
    warehouses: {
      type: Array,
      required: true
    },
    vendors: {
      type: Array,
      required: true
    },
    products: {
      type: Array,
      required: true
    },
    transactions: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      copyOfWarehouse: null,
      Warehouse: Warehouse
    }
  },
  created() {
    this.copyOfWarehouse = this.selectedWarehouse ? this.Warehouse.copy(this.selectedWarehouse) : null
  },
  methods: {
    onClear() {
      this.copyOfWarehouse = new Warehouse(this.copyOfWarehouse.id)
    },
    onReset() {
      this.copyOfWarehouse = this.Warehouse.copy(this.selectedWarehouse)
    },
    onCancel() {
      this.onReset()
      this.$router.push("/warehouse/overview")
    },
    onSave() {
      this.$emit('save', this.copyOfWarehouse)
      this.$router.push("/warehouse/overview")
    },
    onDelete() {
      this.$emit('delete', this.copyOfWarehouse)
      this.$router.push("/warehouse/overview")
    },
    findSelectedWarehouseFromRoute() {
      return this.warehouses.find(warehouse => warehouse.id === parseInt(this.$route.params.id))
    }
  },
  watch: {
    "$route.params.id"() {
      this.copyOfWarehouse = this.selectedWarehouse ? this.Warehouse.copy(this.selectedWarehouse) : null
    }
  }
}

</script>

<style scoped>
.img {
  width: 150px;
  height: 150px;
}
</style>
