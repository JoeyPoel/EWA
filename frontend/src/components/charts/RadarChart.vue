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
    return {
      // TODO: Use the colors from the vuetify theme declared in vuetify.js
      colors: {
        background: '#f8f8f8',
        surface: '#ffffff',
        primary: '#c7d02c',
        'primary-darken-1': '#572700',
        secondary: '#333333',
        'secondary-darken-1': '#bfbfbf',
      }
    }
  },
  mounted() {
    this.chartsData.datasets.forEach((dataset, index) => {
      dataset.backgroundColor = this.createGradient(
          this.colors['primary'],
          this.colors['primary-darken-1'],
          this.colors['secondary-darken-1'],
          index
      )
    })
  },
  methods: {
    createGradient: function (color1, color2, color3, index) {
      const ctx = this.$refs.canvas.getContext('2d')
      const gradientStroke = ctx.createLinearGradient(500, 0, 100, 0);
      const ratio = 1 / this.chartsData.datasets.length * index;
      gradientStroke.addColorStop(0, color1);
      gradientStroke.addColorStop(ratio, color2);
      gradientStroke.addColorStop(1, color3);
      return gradientStroke
    }
  },
}
</script>

<style scoped>

</style>