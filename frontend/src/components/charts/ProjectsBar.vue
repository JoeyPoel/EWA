<template>
  <v-container v-if="chartData">
    <v-card title="Projects" class="text-center elevation-2">
      <base-bar-chart :options="chartOptions" :charts-data="chartData"/>
      <v-card-actions>
        <v-container fluid="true">
          <v-row>
            <v-col cols="8">
              <v-row>
                <v-text-field v-model="startDate" label="Start Date" type="date" @change="updateChartData"/>
                <v-text-field v-model="endDate" label="End Date" type="date" @change="updateChartData"/>
              </v-row>
            </v-col>
            <v-col cols="4">
              <v-row>
                <v-select v-model="interval" :items="intervalItems" label="Interval" @change="updateChartData"/>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-card-actions>
    </v-card>
  </v-container>
  <v-container v-else>
    <v-progress-circular indeterminate size="64"/>
  </v-container>
</template>

<script>

import BaseBarChart from "@/components/base/BaseBarChart.vue";

export default {
  name: "ProjectsBar",
  inject: ['chartsService'],
  components: {
    BaseBarChart
  },
  data() {
    return {
      interval: 'month',
      menu: false,
      startDate: new Date (new Date().setMonth(new Date().getMonth() - 3)).toISOString().substring(0, 10),
      endDate: new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().substring(0, 10),
      chartData: null,
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
      },
      intervalItems: [
        {title: 'Day', value: 'day'},
        {title: 'Week', value: 'week'},
        {title: 'Month', value: 'month'},
      ],
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
  watch: {
    chartData(val) {
      val || this.updateChartData();
    },
    interval(val){
      if (val){
        this.updateChartData();
      }
    },
  },
  mounted() {
    this.updateChartData();
  },
  methods: {
    async updateChartData() {
      console.log(this.warehouseId, this.startDate, this.endDate, this.interval)
      this.chartData = await this.getChartData(this.warehouseId, this.startDate, this.endDate, this.interval);
      console.log(this.chartData)
    },
    async getChartData(warehouseId, startDate, endDate, interval) {
      if (warehouseId) {
        return await this.chartsService.asyncProjectsBarByInterval(warehouseId, startDate, endDate, interval)
      } else {
        return await this.chartsService.asyncProjectsBarByIntervalAllWarehouses(startDate, endDate, interval)
      }
    }
  },
  props: {
    warehouseId: {
      type: Number,
      required: false
    },
  }
}
</script>
