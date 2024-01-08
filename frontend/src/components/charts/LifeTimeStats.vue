<template>
  <v-container v-if="data" :fluid="true">
    <v-card class="mt-1 font-weight-bold">
      <v-card-title class="bg-secondary text-center"><h5 class="fs-3">Lifetime Stats</h5></v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="6">
            <v-card class="mt-1 font-weight-bold">
              <v-card-title class="text-center fs-4">Users</v-card-title>
              <v-card-text class="text-center font-weight-medium fs-5">{{ data.totalEmployees }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card class="mt-1 font-weight-bold">
              <v-card-title class="text-center fs-4">Teams</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalTeams }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card class="mt-1 font-weight-bold">
              <v-card-title class="text-center fs-4">Projects</v-card-title>
              <v-card-text class="text-center font-weight-medium">
                <span class="m-2">Total: {{ data.totalProjects }}</span>
                <span class="m-2">Finished {{ data.totalFinishedProjects }}</span>
              </v-card-text>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card class="mt-1 font-weight-bold">
              <v-card-title class="text-center fs-4">Materials Used</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalMaterialsUsed }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card class="mt-1 font-weight-bold">
              <v-card-title class="text-center fs-4">Orders</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalOrders }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card class="mt-1 font-weight-bold">
              <v-card-title class="text-center fs-4">Product Cost</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalProductCost }}</v-card-text>
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <slot name="warehouse"></slot>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
  <no-data-component v-else/>
</template>
<script>
import NoDataComponent from "@/components/charts/NoDataComponent.vue";

export default {
  name: "LifeTimeStats",
  components: {NoDataComponent},
  inject: ['chartsService'],
  props: {
    warehouseId: {
      type: Number,
      required: false
    }
  },
  watch: {
    warehouseId() {
      this.updateChartData();
    }
  },
  data() {
    return {
      data: null,
    }
  },
  async mounted() {
    await this.updateChartData();
  },
  methods: {
    async updateChartData() {
       this.data = this.warehouseId ? await this.chartsService.asyncLifetimeStatistics(this.warehouseId) :
           await this.chartsService.asyncLifetimeStatisticsAll();
    }
  }
}
</script>