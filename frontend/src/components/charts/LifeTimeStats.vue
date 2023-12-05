<template>
  <v-container v-if="data" fluid="true">
    <v-card class="text-center elevation-2 mb-3">
      <v-card-text>
        <v-row>
          <v-col cols="3">
            <v-card class="elevation-1" outlined>
              <v-card-title class="text-center">Total Users</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalEmployees }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Teams</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalTeams }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Warehouses</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalWarehouses }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Projects</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalProjects }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Finished Projects</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalFinishedProjects }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Materials Used</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalMaterialsUsed }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Orders</v-card-title>
              <v-card-text class="text-center font-weight-medium">{{ data.totalOrders }}</v-card-text>
            </v-card>
          </v-col>
          <v-col cols="3">
            <v-card class=" elevation-1" outlined>
              <v-card-title class="text-center">Total Product Cost</v-card-title>
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
  <v-container v-else class="text-center">
    <v-progress-circular indeterminate size="64"/>
  </v-container>
</template>
<script>
export default {
  name: "LifeTimeStats",
  inject: ['chartsService'],
  data() {
    return {
      data: null,
    }
  },
  async mounted() {
    this.data = await this.chartsService.asyncLifetimeStatisticsAll();
    console.log(this.data)
    console.log("Here")
  },
}
</script>