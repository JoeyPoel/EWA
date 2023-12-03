<template>
  <ChartLine :data="this.chartsData" :options="options"/>
</template>

<script>
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
import {Line as ChartLine} from 'vue-chartjs'
import {ChartsData} from "@/models/charts/ChartsData";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)

export default {
  name: "LineChart",
  components: {
    ChartLine
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