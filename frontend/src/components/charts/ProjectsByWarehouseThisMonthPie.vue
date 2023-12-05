<template>
  <v-container v-if="data">
    <doughnut :data="data" :options="options"/>
  </v-container>
  <v-container v-else>
    <h6>Loading...</h6>
    <v-progress-circular indeterminate size="64"/>
  </v-container>
</template>

<script>
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import {Doughnut} from 'vue-chartjs'

ChartJS.register(ArcElement, Tooltip, Legend)
export default {
  name: "ProjectsByWarehouseThisMonthPie",
  inject: ['chartsService'],
  components:{
    Doughnut
  },
  data() {
    return {
      data: null,
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  async mounted() {
     this.data = await this.chartsService.asyncProjectsPieByStatusThisMonth(2);
  },
}
</script>