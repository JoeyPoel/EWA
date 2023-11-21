<template>
  <div class="m-0 border border-2 rounded border-light-subtle">
    <div class="row m-0 justify-content-center border border-2 rounded border-light-subtle ">
      <WarehouseNewProductCategoryStorageCapacityComponent @addMissingCategory="addCategoryCapacity"/>
    </div>
    <div class="row m-0 justify-content-center">
      <div class="col m-0 align-self-center">
        <div class="row m-0 justify-content-center">
          <div class="scrollPanel">
            <table>
              <thead>
              <tr>
                <th class="fw-light">Category Name</th>
                <th class="fw-light">Minimum stock level</th>
                <th class="fw-light">Capacity</th>
              </tr>
              </thead>
              <tbody>
              <tr class="border border-light-subtle border-1" v-for="category in warehouseProductCategoryCapacities"
                  :key="category.id">
                <td>
                  <div v-if="!isEditing">
                    {{ category.name }}
                  </div>
                  <div v-else>
                    <input class="form-control" id="quantity" type="text"
                           v-model="category.name"/>
                  </div>
                </td>
                <td>
                  <div v-if="!isEditing">
                    {{ category.minimumStockLevel }}
                  </div>
                  <div v-else>
                    <input class="form-control" id="quantity" type="number"
                           v-model="category.minimumStockLevel"/>
                  </div>
                </td>
                <td>{{ category.capacity }}</td>
                <td>
                  <div class="row p-1">
                    <a class="link-info" @click="!isEditing">
                      Edit
                    </a>
                    <a class="link-success " @click="saveCategoryCapacity(category)">
                      Save
                    </a>
                    <a class="link-danger" @click="removeCategoryCapacity(category)">
                      Remove
                    </a>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WarehouseNewProductCategoryStorageCapacityComponent
  from "@/components/warehouses/WarehouseNewProductCategoryStorageCapacityComponent.vue";

export default {
  name: "WarehouseDetailInventoryComponent",
  components: {WarehouseNewProductCategoryStorageCapacityComponent},
  inject: ['productsService', 'warehousesService'],
  props: {},
  data() {
    return {
      lastId: 1000,
      isActive: true,
      warehouseProductCategoryCapacities: [],
      isEditing: false,
    }
  },
  async created() {
    this.warehouseProductCategoryCapacities = await this.warehousesService
        .asyncGetWarehouseCapacityCategories(this.findWarehouseIdFromRoute());
  },
  async mounted() {
    this.warehouseProductCategoryCapacities = await this.warehousesService
        .asyncGetWarehouseCapacityCategories(this.findWarehouseIdFromRoute());
  },
  computed: {},
  methods: {
    addCategoryCapacity(category) {
      this.$emit('add-category', category)
    },
    removeCategoryCapacity(category) {
      this.$emit('remove-category-capacity', category)
    },
    saveCategoryCapacity(category) {
      this.$emit('save-category-capacity', category)
    },
    findWarehouseIdFromRoute() {
      return this.$route.params.id
    },
  },
}
</script>

<style scoped>
.scrollPanel {
  max-height: 40vh;
  overflow-y: auto;
}
</style>