<template>
  <div v-if="selectedWarehouse && copyOfWarehouse" class="row detail justify-content-center">
      <div class="row">
        <form class="col">
          <h3>Warehouse {{ selectedWarehouse.id }}</h3>
          <p class="text-muted">Details</p>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="name">Name</label>
            </div>
            <div class="col col-8">
              <input class="form-control" id="name" type="text" v-model="this.copyOfWarehouse.name"/>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="location">Location</label>
            </div>
            <div class="col col-8">
              <select class="form-control" id="location" v-model="this.copyOfWarehouse.location">
                <option v-for="location in Warehouse.locationList" :key="location">
                  {{ location }}
                </option>
              </select>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="postcode">Postcode</label>
            </div>
            <div class="col col-8">
              <input class="form-control" id="postcode" type="text" v-model="this.copyOfWarehouse.postcode"/>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="country">Country</label>
            </div>
            <div class="col col-8">
              <input class="form-control" id="country" type="text" v-model="this.copyOfWarehouse.country"/>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="contactName">Contact Name</label>
            </div>
            <div class="col col-8">
              <input class="form-control" id="contactName" type="text" v-model="this.copyOfWarehouse.contactName"/>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="contactEmail">Contact Email</label>
            </div>
            <div class="col col-8">
              <input class="form-control" id="contactEmail" type="text" v-model="this.copyOfWarehouse.contactEmail"/>
            </div>
          </div>
          <div class="mt-1 mb-1 row">
            <div class="col col-4 align-self-center text-end">
              <label class="form-label" for="contactPhone">Contact Phone</label>
            </div>
            <div class="col col-8">
              <input class="form-control" id="contactPhone" type="text" v-model="this.copyOfWarehouse.contactPhone"/>
            </div>
          </div>
        </form>
      </div>
      <div class="py-1">
        <div class="row p-1 justify-content-between">
          <button class="btn col col-3 align-self-center btn-secondary" @click="onReset()"
                  :disabled="!hasChanged">
            Reset
          </button>
          <button class="btn col col-3 btn btn-secondary" @click="onClear()" :disabled="!hasChanged">
            Clear
          </button>
          <button class="btn col col-3 btn btn-secondary" @click="onCancel()" :disabled="!hasChanged">
            Cancel
          </button>
        </div>
        <div class="row p-1 justify-content-between">
          <button class="btn col col-5 btn btn-danger" :disabled="hasChanged"
                  @click="onDelete()">
            Delete
          </button>
          <button class="btn col col-5 btn " @click="onSave()" :class="hasChanged ? 'btn-success' : 'btn-secondary'"
                  :disabled="!hasChanged">
            Save
          </button>
        </div>
      </div>
    </div>
<!--    <div class="col col-8">-->
<!--      <WarehouseDetailInventoryComponent :selected-warehouse="selectedWarehouse" :warehouses="warehouses"-->
<!--                                         :products="products" :vendors="vendors" :transactions="transactions"-->
<!--                                         :inventory="inventories" @save-product="saveProduct"-->
<!--      @remove-product="removeProduct" @add-product="addProduct"/>-->
<!--    </div>-->
<!--  </div>-->
</template>

<script>
import {Warehouse} from "@/models/Warehouse";
// import WarehouseDetailInventoryComponent from "@/components/warehouses/WarehouseStorageCapacityComponent.vue";

export default {
  name: "WarehouseDetailComponent",
  // components: {WarehouseDetailInventoryComponent},
  computed: {
    Warehouse() {
      return Warehouse
    },
    selectedWarehouse() {
      return this.findSelectedWarehouseFromRoute();
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
    selected: {
      type: Warehouse,
      required: false
    },

    // vendors: {
    //   type: Array,
    //   required: true
    // },
    // products: {
    //   type: Array,
    //   required: true
    // },
    // transactions: {
    //   type: Array,
    //   required: true
    // },
    // inventories: {
    //   type: Array,
    //   required: true
    // }
  },
  data() {
    return {
      copyOfWarehouse: null,
    }
  },
  created() {
    this.copyOfWarehouse = this.selected ? Warehouse.copy(this.selected) : null;
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
      this.copyOfWarehouse = this.Warehouse.copy(this.selectedWarehouse)
    },
    onDelete() {
      this.$emit('delete', this.copyOfWarehouse)
    },
    findSelectedWarehouseFromRoute() {
      return this.warehouses.find(warehouse => warehouse.id === parseInt(this.$route.params.id))
    },
    saveProduct(product) {
      this.$emit('save-product', product)
    },
    removeProduct(product) {
      this.$emit('remove-product', product)
    },
    addProduct(product) {
      this.$emit('add-product', product)
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
  width: 100px;
  height: 80px;
}
</style>
