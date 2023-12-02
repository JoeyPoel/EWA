<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Line Chart</h5>
          </v-card-title>
          <v-card-text>
            <line-chart></line-chart>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Bar Chart</h5>
          </v-card-title>
          <v-card-text>
            <bar-chart></bar-chart>
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
            <pie-chart></pie-chart>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>
            <h5>Radar Chart</h5>
          </v-card-title>
          <v-card-text>
            <radar-chart></radar-chart>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import {defineComponent} from "vue";
import PieChart from "@/components/charts/PieChart.vue";
import RadarChart from "@/components/charts/RadarChart.vue";
import LineChart from "@/components/charts/LineChart.vue";
import BarChart from "@/components/charts/BarChart.vue";

export default defineComponent({
  components: {BarChart, LineChart, RadarChart, PieChart}
})
</script>

<style scoped>

</style>