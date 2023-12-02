<template>
  <Chart
      ref="chartRef"
      type="bar"
      :data="this.chartConfig.data"
      :options="this.chartConfig.options"
      @click="onClick"
  />
</template>

<script>
import {ref} from 'vue'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  // eslint-disable-next-line no-unused-vars
  InteractionItem
} from 'chart.js'
import {
  // eslint-disable-next-line no-unused-vars
  ChartComponentRef,
  Chart,
  getDatasetAtEvent,
  getElementAtEvent,
  getElementsAtEvent
} from 'vue-chartjs'
import * as chartConfig from "@/views/admin/dashboard/barChartConfig";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'App',
  components: {
    Chart
  },
  data() {
    return {
      chartConfig
    }
  },
  setup() {
    function datasetAtEvent(dataset) {
      if (!dataset.length) return

      const datasetIndex = dataset[0].datasetIndex

      console.log('dataset', this.data.datasets[datasetIndex].label)
    }

    function elementAtEvent(element) {
      if (!element.length) return

      const {datasetIndex, index} = element[0]

      console.log(
          'element',
          this.data.labels[index],
          this.data.datasets[datasetIndex].data[index]
      )
    }

    function elementsAtEvent(elements) {
      if (!elements.length) return

      console.log('elements', elements)
    }

    const chartRef = ref(null)

    function onClick(event) {
      const {
        value: {chart}
      } = chartRef

      if (!chart) {
        return
      }

      datasetAtEvent(getDatasetAtEvent(chart, event))
      elementAtEvent(getElementAtEvent(chart, event))
      elementsAtEvent(getElementsAtEvent(chart, event))
    }

    return {
      chartRef,
      onClick,
      ...chartConfig
    }
  }
}
</script>