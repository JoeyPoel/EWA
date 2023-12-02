<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>
            <h5>Line Chart</h5>
          </v-card-title>
          <v-card-text>
            <line-chart></line-chart>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
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
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>
            <h5>Pie Chart</h5>
          </v-card-title>
          <v-card-text>
            <pie-chart></pie-chart>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" md="6">
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
import PieChart from "@/views/admin/dashboard/PieChart.vue";
import RadarChart from "@/views/admin/dashboard/RadarChart.vue";
import LineChart from "@/views/admin/dashboard/LineChart.vue";
import BarChart from "@/views/admin/dashboard/BarChart.vue";

export default defineComponent({
  components: {BarChart, LineChart, RadarChart, PieChart}
})
</script>

<style scoped>

</style>