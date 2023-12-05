<template>
  <v-row v-if="data">
    <v-col>
      <v-container>
        <v-card title="All-time Statistics" class="text-center">
          <v-card-text>
            <v-row>
              <v-col cols="4" >
                <v-card-title>Employees</v-card-title>
                <v-card-text>{{ data.totalEmployees }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Teams</v-card-title>
                <v-card-text>{{ data.totalTeams }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Warehouses</v-card-title>
                <v-card-text>{{ data.totalWarehouses }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Projects</v-card-title>
                <v-card-text>{{ data.totalProjects }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Finished Projects</v-card-title>
                <v-card-text>{{ data.totalFinishedProjects }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Materials Used</v-card-title>
                <v-card-text>{{ data.totalMaterialsUsed }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Solar Panels Installed</v-card-title>
                <v-card-text>{{ data.totalSolarPanelsInstalled }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Product Cost</v-card-title>
                <v-card-text>{{ data.totalProductCost }}</v-card-text>
              </v-col>
              <v-col cols="4">
                <v-card-title>Orders</v-card-title>
                <v-card-text>{{ data.totalOrders }}</v-card-text>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-container>
    </v-col>
  </v-row>
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