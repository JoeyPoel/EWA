<template>
  <div class="col container-fluid">
    <div class="row">
      <div class="col px-5 text-center border border-2 border-light-subtle rounded-4 mx-2">
        <div class="row pt-1">
          <div class="col col-auto align-self-center">
            <button class="btn btn-lg btn-success" @click="onNewWarehouse()">
              New Warehouse
            </button>
          </div>
          <div class="col col-10">
            <div class="row flex-nowrap overflow-auto p-2 border border-2 border-light-subtle rounded-4"
                 ref="scrollPanel">
              <div class="warehouse col col-2 border border-secondary-subtle rounded-2 justify-content-center mx-1 "
                   type="button"
                   v-for="warehouse in warehouses" :key="warehouse.id" @click="selectWarehouse(warehouse)"
                   :class="{active:this.findSelectedWarehouseFromRoute() === warehouse}">
                <div class="row justify-content-center">
                  <img class="card-img rounded" :src="Warehouse.getImage()" alt="Card image cap">
                </div>
                <div class="mt-1">
                  <h6 class="fw-medium">Warehouse {{ warehouse.id }}</h6>
                  <p class="fw-light">
                    <strong>Name:</strong> {{ warehouse.name }}
                    <br><strong>Location:</strong> {{ warehouse.location }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <router-view :warehouses="warehouses" @delete="onDelete" @save="onSave"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Warehouse} from '@/models/warehouse.js';

export default {
  name: "WarehouseOverviewComponent",
  components: {},
  data() {
    return {
      warehouses: [],
      lastId: 10000,
      isActive: true,
      Warehouse: Warehouse
    }
  },

  created() {
    for (let i = 0; i < 8; i++) {
      this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
      this.warehouses.push(
          Warehouse.createDummyWarehouse(this.lastId)
      )
    }
  },

  methods: {
    onNewWarehouse() {
      this.lastId = this.lastId + Math.floor(Math.random() * 3) + 1
      this.warehouses.push(
          Warehouse.createDummyWarehouse(this.lastId)
      )
      this.$router.push("/warehouse/overview/" + this.lastId)
      this.$nextTick(() => {
        this.$refs.scrollPanel.scrollLeft = this.$refs.scrollPanel.scrollWidth
      })
    },
    onDelete(warehouse) {
      this.warehouses = this.warehouses.filter(c => c !== warehouse)
    },
    onSave(warehouse) {
      this.warehouses = this.warehouses.map(c => c === warehouse ? warehouse : c)
    },
    selectWarehouse(warehouse) {
      this.$router.push("/warehouse/overview/" + warehouse.id)
    },
    findSelectedWarehouseFromRoute() {
      const id = parseInt(this.$route.params.id)
      return this.warehouses.find(c => c.id === id)
    }
  }
}
</script>

<style scoped>
.card-img {
  width: 50%;
  height: 50%;
}

.warehouse {
  min-width: 100px;
  min-height: fit-content;
  max-width: 200px;
  max-height: 250px;
}

.active {
  background: lightslategray;
}
</style>