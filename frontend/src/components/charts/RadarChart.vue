<template>
  <PolarArea :data="this.chartsData" :options="options" />
</template>

<script>
import {
  Chart as ChartJS,
  RadialLinearScale,
  ArcElement,
  Tooltip,
  Legend,
} from 'chart.js'
import { PolarArea } from 'vue-chartjs'
import {ChartsData} from "@/models/charts/ChartsData";

ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend)

export default {
  name: 'App',
  components: {
    PolarArea
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
  data() {
  },
  watch: {
    labels: function (newVal, oldVal) {
      this.chartsData.labels = newVal
    },
    datasets: function (newVal, oldVal) {
      this.chartsData.datasets = newVal
    },
    options: function (newVal, oldVal) {
      this.chartsData.options = newVal
    }
  },
}
</script>

<style scoped>

</style>