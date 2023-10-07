<template>
  <div v-if="this.copyOfWarehouse" class="row detail justify-content-center  p-0 px-3 mt-1">
    <div class="col col-3 col-auto align-self-center">
      <img class="img-fluid" alt="Cabin image" :src="Warehouse.getImage()"/>
    </div>
    <div class="col-md-6 col-xl-8">
      <form class="row p-1 p-xl-4">
        <h3>Warehouse {{ selectedWarehouse.id }}</h3>
        <p class="text-muted">Details</p>
        <div class="mt-1 mb-1 row">
          <div class="col">
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
      <div class="row p-2 m-0">
        <div class="col col-auto align-self-center">
          <button class="btn btn-lg btn-success" @click="onSave()" :disabled="!hasChanged">
            Save
          </button>
        </div>
        <div class="col col-auto align-self-center">
          <button class="btn btn-lg btn-danger" @click="onDelete()">
            Delete
          </button>
        </div>
        <div class="col col-auto align-self-center">
          <button class="btn btn-lg btn-secondary" @click="onReset()" :disabled="!hasChanged">
            Reset
          </button>
        </div>
        <div class="col col-auto align-self-center">
          <button class="btn btn-lg btn-secondary" @click="onClear()" :disabled="!hasChanged">
            Clear
          </button>
        </div>
        <div class="col col-auto align-self-center">
          <button class="btn btn-lg btn-secondary" @click="onCancel()" :disabled="!hasChanged">
            Cancel
          </button>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <div class="row justify-content-center">
      <div class="col col-auto align-self-center">
        <h3>No warehouse selected</h3>
      </div>
    </div>
  </div>
</template>

<script>
import {Warehouse} from "@/models/warehouse";

export default {
  name: "WarehouseDetailComponent",
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
    }
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
</style>
