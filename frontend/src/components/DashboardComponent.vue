<template>
  <section class="container mt-4">
    <div class="row">
      <div class="col-md-3">
        <div class="card text-center">
          <div class="card-body">
            <h5 class="card-title">Total panels</h5>
            <h2 class="card-number">{{ panelCount }}</h2>
          </div>
          <div class="card-footer">
            <button class="btn btn-primary">More Info</button>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center">
          <div class="card-body">
            <h5 class="card-title">Total teams</h5>
            <h2 class="card-number">{{ teamCount }}</h2>
          </div>
          <div class="card-footer">
            <button class="btn btn-primary">More Info</button>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center">
          <div class="card-body">
            <h5 class="card-title">Total warehouses</h5>
            <h2 class="card-number">{{ warehouseCount }}</h2>
          </div>
          <div class="card-footer">
            <button class="btn btn-primary">More Info</button>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center">
          <div class="card-body">
            <h5 class="card-title">Total projects</h5>
            <h2 class="card-number">{{ projectCount }}</h2>
          </div>
          <div class="card-footer">
            <button class="btn btn-primary">More Info</button>
          </div>
        </div>
      </div>
    </div>

    <div class="container my-5"> <!-- add :childs to router like warehouse:id so list items can navigate -->
      <div class="row">
        <div class="col-md-4">
          <div class="card">
            <h5 class="card-title text-center mt-3">Planned projects this week</h5>
            <div class="card-body">
              <!-- List of projects generated with loop (dummy) -->
              <div class="card mt-2" v-for="project in projects" :key="project.name">
                <div class="card-body">
                  <p class="card-text">{{ project.name }}</p>
                </div>
              </div>

            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="card">
            <h5 class="card-title text-center mt-3">Warehouses</h5>
            <div class="card-body">
              <!-- List of warehouses generated with loop -->
              <div class="card mt-2" v-for="warehouse in warehouses" :key="warehouse.id">
                <div class="card-body">
                  <p class="card-text">{{ warehouse.name }}</p>
                </div>
              </div>

            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="card">
            <h5 class="card-title text-center mt-3">installer teams</h5>
            <div class="card-body">
              <!-- List of teams generated with loop (dummy) -->
              <div class="card mt-2" v-for="team in teams" :key="team.name">
                <div class="card-body">
                  <p class="card-text">{{ team.name }}</p>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>

import {warehouse} from "@/models/warehouse.js";
export default {
  name: "DashboardComponent",

  data() {
    return {
      warehouses: [],
      warehouseCount: 0,
      panelCount: 60,

      teams: [
        {name: "Team 1"},
        {name: "Team 2"},
        {name: "Team 3"},
        {name: "Team 4"}
      ],
      teamCount: 4,

      projects: [
        {name: "Project 1"},
        {name: "Project 2"},
        {name: "Project 3"},
        {name: "Project 4"}
      ],
      projectCount: 4,
    }
  },
  created() {
    // warehouses
    for (let i = 0; i < 5; i++) {
      this.warehouses.push(warehouse.createDummyWarehouse(i + 1))
      this.warehouseCount++;
    }
  },
}
</script>

<style scoped>

</style>