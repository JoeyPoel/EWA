<template>
  <v-container v-if="data" :fluid="true">
    <v-card>
      <v-card-text>
        <Chart :data="this.data" :options="options" type="bar"/>
      </v-card-text>
    </v-card>
  </v-container>
  <no-data-component v-else/>
</template>

<script>
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from 'chart.js'
import {Chart,} from 'vue-chartjs'
import NoDataComponent from "@/components/charts/NoDataComponent.vue";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'App',
  components: {
    NoDataComponent,
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
  mounted() {
    if (this.chartsData) {
      this.data = this.chartsData;
      this.setColor()
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