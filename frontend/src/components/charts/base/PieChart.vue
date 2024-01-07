<template>
  <v-container v-if="data">
    <pie :data="this.data" :options="options"/>
  </v-container>
  <v-container v-else>
    <h6>Loading...</h6>
    <v-progress-circular indeterminate size="64"/>
  </v-container>
</template>

<script>
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import { Pie } from 'vue-chartjs'

ChartJS.register(ArcElement, Tooltip, Legend)

export default {
  name: 'PieChart',
  components: {
    Pie
  },

  props: {
    chartsData: {
      type: Object,
      required: true
    },
    options: {
      type: Object,
      required: false
    }
  },

  data() {
    return { data: this.chartsData ? { ...this.chartsData } : null };
  },

  watch: {
    chartsData: {
      immediate: true,
      handler(newValue) { if (newValue) { this.updateData(newValue) } }
    }
  },

  mounted() {
    if (this.chartsData) {
      this.updateData(this.chartsData)
    }
  },

  methods: {
    updateData(newValue) {
      this.data = { ...newValue };
      this.setColor();
    },

    setColor() {
      const backgroundColor = [];
      this.data.datasets[0].data.forEach((_, index) => {
        backgroundColor.push(index % 2 === 0 ? '#c7d02c' : '#572700');
      });
      this.data.datasets[0].backgroundColor = backgroundColor;
    }
  },
}
</script>