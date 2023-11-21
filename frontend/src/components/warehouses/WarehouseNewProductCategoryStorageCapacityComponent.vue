<template>
  <div class="row">
    <div class="col align-self-center ">
      <div class="row">
        <div class="col col-3 align-self-center text-start">
          <label class="form-label" for="category">Category</label>
        </div>
        <div class="col col-9">
          <select class="form-control" id="category" v-model="warehouseProductCategoryCapacity.productCategory">
            <option v-for="category in missingCategories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
        </div>
      </div>
      <div class="row mt-1">
        <div class="col col-7 align-self-center text-start">
          <label class="form-label" for="quantity">Capacity</label>
        </div>
        <div class="col col-5">
          <input class="form-control" id="quantity" type="number"
                 v-model="warehouseProductCategoryCapacity.capacity"/>
        </div>
      </div>
      <div class="row mt-1">
        <div class="col col-7 align-self-center text-start">
          <label class="form-label" for="quantity">Minimum stock level</label>
        </div>
        <div class="col col-5">
          <input class="form-control" id="quantity" type="number"
                 v-model="warehouseProductCategoryCapacity.minimumStockLevel"/>
        </div>
      </div>
    </div>
    <div class="col col-3">
      <div class="row ">
        <button class="btn btn-success" @click="addCategoryCapacity()">
          Add category capacity
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import {WarehouseProductCategoryCapacity} from "@/models/WarehouseProductCategoryCapacity";

export default {
  name: "WarehouseNewProductCategoryStorageCapacityComponent",
  inject: ['warehousesService'],
  data() {
    return {
      missingCategories: [],
      warehouseProductCategoryCapacity: new WarehouseProductCategoryCapacity(),
    };
  },
  async created() {
    this.missingCategories = await this.warehousesService
        .asyncGetMissingWarehouseCapacityCategories(this.findWarehouseIdFromRoute());
  },
  async mounted() {
    this.missingCategories = await this.warehousesService
        .asyncGetMissingWarehouseCapacityCategories(this.findWarehouseIdFromRoute());
  },
  methods: {
    findWarehouseIdFromRoute() {
      return this.$route.params.warehouseId;
    },
    async addCategoryCapacity() {
      await this.warehousesService.asyncAddWarehouseCapacityByWarehouseId(
          this.findWarehouseIdFromRoute(),
          this.warehouseProductCategoryCapacity
      );
      this.warehouseProductCategoryCapacity = new WarehouseProductCategoryCapacity();
    }
  }
}
</script>

<style scoped>

</style>