<template>
  <v-container v-if="data">
    <v-card title="Projects this Month" class="text-center elevation-2">
      <base-pie-chart :options="options" :charts-data="this.data"/>
    </v-card>
  </v-container>
  <v-container v-else>
    <h6>Loading...</h6>
    <v-progress-circular indeterminate size="64"/>
  </v-container>
</template>

<script>
import BasePieChart from "@/components/charts/base/PieChart.vue";

export default {
  name: "ProjectsThisMonthPie",
  inject: ['chartsService'],
  components:{
    BasePieChart
  },
  data() {
    return {
      data: null,
      options: {
        responsive: true,
        maintainAspectRatio: false
      },

    }
  },
  async mounted() {
     this.data = await this.chartsService.asyncProjectsPieByStatusThisMonthAll();
  },
}
</script>