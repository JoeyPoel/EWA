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
            <router-link class="btn btn-primary" to="/product">
              More Info
            </router-link>
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
            <router-link class="btn btn-primary" :to="{ name: 'User' }">
              More Info
            </router-link>
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
            <router-link class="btn btn-primary" to="/warehouse/overview">
              More Info
            </router-link>
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
            <router-link class="btn btn-primary" to="/project">
              More Info
            </router-link>
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

export default {
  name: "DashboardComponent",
  inject: ["warehousesService", "teamsService", "projectsService"],
  data() {
    return {
      warehouses: [],
      warehouseCount: 0,
      panelCount: 60,
      teams: [],
      teamCount: 0,
      projects: [],
      projectCount: 0,
    }
  },
  async created() {
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
    this.warehouseCount = this.warehouses.length;
    this.teams = await this.teamsService.asyncFindAll();
    this.teamCount = this.teams.length;
    this.projects = await this.projectsService.asyncFindAll();
    this.projectCount = this.projects.length;
  },
  async mounted() {
    this.warehouses = await this.warehousesService.asyncGetAllWarehouses();
    this.warehouseCount = this.warehouses.length;
    this.teams = await this.teamsService.asyncFindAll();
    this.teamCount = this.teams.length;
    this.projects = await this.projectsService.asyncFindAll();
    this.projectCount = this.projects.length;
  }
}
</script>

<style scoped>

</style>