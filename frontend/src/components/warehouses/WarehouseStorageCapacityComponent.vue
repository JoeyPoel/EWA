<template>
  <div class="m-0 border border-2 rounded border-light-subtle">
    <div class="row m-0 justify-content-center">
      <div class="col m-0 align-self-center">
        <div class="row m-0 justify-content-center">
            <table class="table table-hover">
              <thead>
              <tr>
                <th class="fw-light">Category Name</th>
                <th class="fw-light">Minimum stock level</th>
                <th class="fw-light">Capacity</th>
                <th class="fw-light">Actions</th>
              </tr>
              </thead>
              <tbody class="scrollPanel">
              <warehouse-capacity-row-component v-for="category in warehouseProductCategoryCapacities"
                                                :key="category.id"
                                                :category="category"
                                                @save-category-capacity="saveCategoryCapacity"
                                                @remove-category-capacity="removeCategoryCapacity"/>
              </tbody>
            </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import WarehouseCapacityRowComponent from "@/components/warehouses/WarehouseCapacityRowComponent.vue";

export default {
  name: "WarehouseDetailInventoryComponent",
  components: {
    WarehouseCapacityRowComponent
  },
  inject: ['productsService', 'warehousesService'],
  props: {},
  data() {
    return {
      lastId: 1000,
      isActive: false,
      warehouseProductCategoryCapacities: [],
      isEditing: false,
    }
  },
  async mounted() {
    await this.getCapacities()
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
    async getCapacities() {
      this.warehouseProductCategoryCapacities = this.$route.params.id ?
          await this.warehousesService.asyncGetWarehouseCapacityCategories(this.$route.params.id) : null;
    },
    mouseOver() {
      this.isActivated = true;
    },
  },
}
</script>

<style scoped>
.scrollPanel {
  max-height: 50vh;
  overflow-y: auto;
}
</style>