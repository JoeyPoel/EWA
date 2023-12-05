<template>
  <Pie :data="this.chartsData" :options="options"/>
</template>

<script>
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js'
import {Pie} from 'vue-chartjs'
import {ChartsData} from "@/models/charts/ChartsData";

ChartJS.register(ArcElement, Tooltip, Legend)

export default {
  name: 'App',
  components: {
    Pie
  },
  props: {
    chartsData: {
      type: ChartsData,
      required: true
    },
    options: {
      type: Object,
      required: false
    }
  },
}
</script>

<style scoped>

</style>