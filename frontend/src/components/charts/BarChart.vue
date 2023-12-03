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

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'App',
  components: {
    Chart
  },
  props: {
    chartsData: {
      type: Object,
      required: false
    },
    options: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      data: this.chartsData
    }
  },
}
</script>

<style scoped>

</style>