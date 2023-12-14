<template>
  <v-container v-if="data" :fluid="true">
    <v-card class="text-center elevation-2">
      <v-card-text>
        <v-row>
          <v-col cols="4" md="2">
            <v-card class="elevation-1" outlined>
              <v-card-title class="text-center">Users</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalEmployees }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="4" md="2">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Teams</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalTeams }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="4" md="2">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Projects</v-card-title>
              <v-card-text class="text-center font-weight-medium">
                <span class="m-2">Total: {{ data.totalProjects }}</span>
                <span class="m-2">Finished {{ data.totalFinishedProjects }}</span>
              </v-card-text>
            </v-card>
          </v-col>
          <v-col cols="4" md="2">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Materials Used</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalMaterialsUsed }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="4" md="2">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Orders</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalOrders }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="4" md="2">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Product Cost</v-card-title>
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