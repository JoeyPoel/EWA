<template>
  <v-container v-if="data">
    <Chart type="bar" :data="this.data" :options="options"/>
  </v-container>
  <v-container v-else>
    <h6>Loading...</h6>
    <v-progress-circular indeterminate size="64"/>
  </v-container>
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
      data: null,
    }
  },
  watch: {
    chartsData: {
      handler() {
        this.data = this.chartsData;
        this.setColor()
      },
      deep: true
    }
  },
  methods: {
    setColor() {
      this.data.datasets.forEach((dataset, index) => {
        dataset.backgroundColor = index % 2 === 0 ? '#c7d02c' : '#572700'
      })
    }
  }
}
</script>

<style scoped>

</style>