<template>
  <Chart type="bar" :data="this.chartsData" :options="options"/>
</template>

<script>
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'
import {Chart,} from 'vue-chartjs'
import {ChartsData} from "@/models/charts/ChartsData";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'App',
  components: {
    Chart
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