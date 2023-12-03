<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Line Chart</h5>
          </v-card-title>
          <v-card-text>
            <!--            <line-chart></line-chart>-->
          </v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Inventory</h5>
          </v-card-title>
          <v-card-text v-if="inventoryBarChartData">
            <bar-chart :charts-data="inventoryBarChartData"/>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Pie Chart</h5>
          </v-card-title>
          <v-card-text>
            <!--            <pie-chart></pie-chart>-->
          </v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Radar Chart</h5>
          </v-card-title>
          <v-card-text>
            <!--            <radar-chart></radar-chart>-->
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import BarChart from "@/components/charts/BarChart.vue";

export default {
  inject: ['chartsService'],
  components: {
    BarChart,
    // LineChart,
    // RadarChart,
    // PieChart
  },
  data() {
    return {
      inventoryBarChartData: null,
    }
  },
  async mounted() {
    await this.getInventoryBarChartData()
  },
  methods: {
    async getInventoryBarChartData() {
      let data = await this.chartsService.asyncFindAllInventoryBar();
      console.log(data);
      data.datasets.push({...data.datasets[0]})
      data.datasets.push({...data.datasets[0]})
      data.datasets.forEach((dataset, index) => {
        dataset.backgroundColor = index % 2 === 0 ? '#c7d02c' : '#572700'
      })
      this.inventoryBarChartData = data;
    },
  }
}
</script>

<style scoped>

</style>