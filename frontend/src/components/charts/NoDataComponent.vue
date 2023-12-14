<script>
export default {
  name: "NoDataComponent"
}
</script>

<template>
  <v-container :fluid="true">
    <v-card>
      <v-card-text>
        <v-row justify="center">
          <v-col class="text-center" cols="12">
            <v-icon color="grey lighten-1" x-large>mdi-chart-line</v-icon>
          </v-col>
          <v-col class="text-center" cols="12">
            <v-progress-circular indeterminate size="64"/>
          </v-col>
          <v-col class="text-center" cols="12">
            <span class="grey--text text--lighten-1">No data to display</span>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<style scoped>

</style>