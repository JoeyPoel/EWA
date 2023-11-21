<template>
  <div class="modal fade" ref="modalRef" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Warehouse Details</h5>
          <button @click="closeModal" type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>
        <div class="card text-center">
          <div class="card-header">
            <div class="card-header">
              <ul class="nav nav-tabs card-header-tabs">
                <li class="nav-item">
                  <a class="nav-link active"
                     aria-current="true"
                     data-bs-target="#details-content"
                     data-bs-toggle="tab">
                    Details
                  </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link"
                      aria-current="false"
                     data-bs-target="#inventory-content"
                     data-bs-toggle="tab">
                    Inventory
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="card-body tab-content">
            <div class="tab-pane fade show active" id="details-content">
              <div>
                <warehouseDetailComponent :warehouses="warehouses" :selected="selectedWarehouse"/>
              </div>
            </div>
            <div class="tab-pane fade" id="inventory-content">
              <div>
                <warehouseDetailInventoryComponent :warehouses="warehouses" :selected="selectedWarehouse"
                                                   :products="products" :inventory="getInventory()"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Modal} from 'bootstrap';
import WarehouseDetailComponent from "@/components/warehouses/WarehouseDetailComponent.vue";
import {Warehouse} from "@/models/Warehouse";
import WarehouseDetailInventoryComponent from "@/components/warehouses/WarehouseDetailInventoryComponent.vue";
export default {
  name: "WarehouseDetailModalComponent",
  inject: ['warehousesService'],
  components: {
    warehouseDetailComponent: WarehouseDetailComponent,
    warehouseDetailInventoryComponent: WarehouseDetailInventoryComponent
  },
  props: {
    warehouses: {
      type: Array,
      required: true
    },
    selectedWarehouse: {
      type: Object,
      required: false
    },
    products: {
      type: Array,
      required: true
    },
    inventories: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      modal: null,
      Warehouse: Warehouse,
    };
  },
  created() {
    console.log(this.inventories)
  },

  mounted() {
    this.modal = new Modal(this.$refs.modalRef);
    if (this.$route.params.id || this.$route.name === "add") {
      this.modal.show();
    }
    this.$refs.modalRef.addEventListener('hidden.bs.modal', this.closeModal);
  },

  beforeUnmount() {
    if (this.$refs.modalRef) {
      this.$refs.modalRef.removeEventListener('hidden.bs.modal', this.closeModal);
    }
  },
  methods: {
    closeModal() {
      this.modal.hide();
      this.$emit('close-modal');
    },
    getInventory() {
      return this.inventories.filter(inventory => inventory.warehouseId === this.selectedWarehouse.id)
    }
  },
};
</script>

<style scoped>
.modal {
  --bs-modal-header-border-color: transparent;
}

.modal-header {
  outline: none;
}

.card {
  --bs-card-border-radius: none;
}
</style>