<template>
  <v-container v-if="data">
    <ChartLine :data="this.data" :options="options"/>
  </v-container>
  <v-container v-else>
    <h6>Loading...</h6>
    <v-progress-circular indeterminate size="64"/>
  </v-container>
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
      deep: true,
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