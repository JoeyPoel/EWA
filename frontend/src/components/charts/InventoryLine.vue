<template>
  <v-container fluid="true">
    <v-card title="Inventory" class="text-center elevation-2 chart">
      <base-line-chart :options="chartOptions" :charts-data="chartData" />
      <v-card-actions>
        <v-container fluid="true">
          <v-row>
            <v-col cols="8">
              <v-row>
                <v-text-field v-model="startDate" label="Start Date" type="date"/>
                <v-text-field v-model="endDate" label="End Date" type="date"/>
              </v-row>
            </v-col>
            <v-col cols="4">
              <v-row>
                <v-select v-model="interval" :items="intervalItems" label="Interval"/>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>

import BaseLineChart from "@/components/charts/base/LineChart.vue";
export default {
  name: "InventoryLine.vue",
  inject: ['chartsService', "productsService"],
  components: {
    BaseLineChart
  },
  props: {
    warehouseId: {
      type: Number,
      required: false
    }
  },
  data() {
    return {
      interval: 'week',
      menu: false,
      productIds: [],
      selectedProductIds: [],
      startDate: new Date (new Date().setMonth(new Date().getMonth() - 12)).toISOString().substring(0, 10),
      endDate: new Date(new Date().setMonth(new Date().getMonth() + 5)).toISOString().substring(0, 10),
      chartData: null,
      chartOptions: {
        responsive: true,
        height: 650
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
    warehouseId() {
      this.updateChartData();
    },
    startDate() {
      this.updateChartData();
    },
    endDate() {
      this.updateChartData();
    },
    interval() {
      this.updateChartData();
    }
  },
  async mounted() {
    await this.loadProductIds();
    this.selectedProductIds = [...this.productIds];
    await this.updateChartData();
  },
  methods: {
    async updateChartData() {
      console.log(this.warehouseId, this.startDate, this.endDate, this.interval)
      this.chartData = await this.getChartData(this.warehouseId, this.startDate, this.endDate, this.interval);
      console.log(this.chartData)
    },
    async loadProductIds() {
      const products = await this.productsService.asyncFindAllActive();
      this.productIds = products.map(product => product.id);
    },
    async getChartData() {
      if (this.warehouseId) {
        return await this.chartsService.asyncInventoryLineByInterval(this.warehouseId,
            this.selectedProductIds,this.startDate, this.endDate, this.interval);
      } else {
        return await this.chartsService.asyncInventoryLineByIntervalAllWarehouses(this.selectedProductIds,
            this.startDate, this.endDate, this.interval);
      }
    }
  }
}
</script>

<style scoped>
canvas {
  height: 500px !important;
}
</style>