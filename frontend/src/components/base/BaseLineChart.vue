<template>
  <v-container v-if="data" :fluid="true">
    <v-card>
      <v-card-text>
        <ChartLine :data="this.data" :options="options"/>
      </v-card-text>
    </v-card>
  </v-container>
  <no-data-component v-else/>
</template>


<script>
import {CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip} from 'chart.js'
import {Line as ChartLine} from 'vue-chartjs'
import {ChartsData} from "@/models/charts/ChartsData";
import NoDataComponent from "@/components/charts/NoDataComponent.vue";

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
    NoDataComponent,
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