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
            <input id="name" v-model="this.copyOfWarehouse.name" class="form-control" type="text"/>
          </div>
        </div>
        <div class="mt-1 mb-1 row">
          <div class="col col-4 align-self-center text-end">
            <label class="form-label" for="location">Location</label>
          </div>
          <div class="col col-8">
            <input id="location" v-model="this.copyOfWarehouse.location" class="form-control" type="text"/>
          </div>
        </div>
        <div class="mt-1 mb-1 row">
          <div class="col col-4 align-self-center text-end">
            <label class="form-label" for="postcode">Postcode</label>
          </div>
          <div class="col col-8">
            <input id="postcode" v-model="this.copyOfWarehouse.postcode" class="form-control" type="text"/>
          </div>
        </div>
        <div class="mt-1 mb-1 row">
          <div class="col col-4 align-self-center text-end">
            <label class="form-label" for="country">Country</label>
          </div>
          <div class="col col-8">
            <input id="country" v-model="this.copyOfWarehouse.country" class="form-control" type="text"/>
          </div>
        </div>
        <div class="mt-1 mb-1 row">
          <div class="col col-4 align-self-center text-end">
            <label class="form-label" for="contactName">Contact Name</label>
          </div>
          <div class="col col-8">
            <input id="contactName" v-model="this.copyOfWarehouse.contactName" class="form-control" type="text"/>
          </div>
        </div>
        <div class="mt-1 mb-1 row">
          <div class="col col-4 align-self-center text-end">
            <label class="form-label" for="contactEmail">Contact Email</label>
          </div>
          <div class="col col-8">
            <input id="contactEmail" v-model="this.copyOfWarehouse.contactEmail" class="form-control" type="text"/>
          </div>
        </div>
        <div class="mt-1 mb-1 row">
          <div class="col col-4 align-self-center text-end">
            <label class="form-label" for="contactPhone">Contact Phone</label>
          </div>
          <div class="col col-8">
            <input id="contactPhone" v-model="this.copyOfWarehouse.contactPhone" class="form-control" type="text"/>
          </div>
        </div>
      </form>
    </div>
    <div class="py-1">
      <div class="row p-1 justify-content-between">
        <button :disabled="!hasChanged" class="btn col col-3 align-self-center btn-secondary"
                @click="onReset()">
          Reset
        </button>
        <button :disabled="!hasChanged" class="btn col col-3 btn btn-secondary" @click="onClear()">
          Clear
        </button>
        <button :disabled="!hasChanged" class="btn col col-3 btn btn-secondary" @click="onCancel()">
          Cancel
        </button>
      </div>
      <div class="row p-1 justify-content-between">
        <button :disabled="hasChanged" class="btn col col-5 btn btn-danger"
                @click="onDelete()">
          Delete
        </button>
        <button :class="hasChanged ? 'btn-success' : 'btn-secondary'" :disabled="!hasChanged" class="btn col col-5 btn "
                @click="onSave()">
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
  inject: ['warehousesService'],
  // components: {WarehouseDetailInventoryComponent},
  computed: {
    hasChanged() {
      return !Warehouse.equals(this.selectedWarehouse, this.copyOfWarehouse)
    }
  },
  data() {
    return {
      selectedWarehouse: null,
      copyOfWarehouse: null,
    }
  },
  created() {
  },
  async mounted() {
    this.selectedWarehouse = await this.findSelectedWarehouseFromRoute();
    this.copyOfWarehouse = Warehouse.copy(this.selectedWarehouse);
  },
  methods: {
    onClear() {
      this.copyOfWarehouse = new Warehouse(this.copyOfWarehouse.id);
    },
    onReset() {
      this.copyOfWarehouse = Warehouse.copy(this.selectedWarehouse);
    },
    onCancel() {
      this.onReset();
      this.$router.push("/warehouse/overview");
    },
    async onSave() {
      const savedWarehouse = await this.warehousesService.asyncUpdateWarehouse(this.selectedWarehouse.id,
          this.copyOfWarehouse);
      if (savedWarehouse) {
        this.selectedWarehouse = savedWarehouse;
        this.copyOfWarehouse = Warehouse.copy(this.selectedWarehouse);
        this.$emit('warehouse-updated', this.copyOfWarehouse)
      } else {
        alert('Error saving warehouse');
      }
    },
    onDelete() {
      this.$emit('delete', this.copyOfWarehouse)
    },
    async findSelectedWarehouseFromRoute() {
      return await this.warehousesService.asyncFindById(this.$route.params.id)
    },
  },
  watch: {
    '$route': async function () {
      this.selectedWarehouse = await this.findSelectedWarehouseFromRoute();
      this.copyOfWarehouse = this.selectedWarehouse ? Warehouse.copy(this.selectedWarehouse) : null;
    },
  }
}

</script>

<style scoped>
.img {
  width: 100px;
  height: 80px;
}
</style>
