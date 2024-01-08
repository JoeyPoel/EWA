<template>
  <entity-data-table :dialog-config="dialogConfig" :filter-config="filterConfig" :table-config="tableConfig"
                     title="Projects" @delete="handleDelete" @save="handleSave"
                     @update-tableConfig="tableConfig = $event" @update-dialogConfig="dialogConfig = $event"
                     @update-filterConfig="filterConfig = $event">
  </entity-data-table>
</template>

<script>
import EntityDataTable from "@/components/base/EntityDataTable.vue";
import {Project} from "@/models/Project";

export default {
  name: "ProjectsV2",
  computed: {
    Project() {
      return Project
    }
  },
  inject: ['projectsService', 'teamsService'],
  components: {EntityDataTable},
  data() {
    return {
      projects: [],
      teams: [],
      tableConfig: {
        entityName: 'Project',
        headers: [
          {title: 'Name', value: 'name', key: 'name'},
          {title: 'Team', key: 'teamName'},
          {title: 'Start Date', value: 'startDate', key: 'startDate'},
          {title: 'End Date', value: 'endDate', key: 'endDate'},
          {title: 'Status', value: 'status', key: 'status'},
          {title: 'Actions', value: 'actions', sortable: false}
        ],
        customValues: [
          {key: 'status', value: 'status'},
          {key: 'teamName', value: 'teamName'}
        ],
        items: this.projects,
        itemsPerPage: 10,
        searchTerm: '',
        actions: [
          {action: 'Details', icon: '$info', color: 'primary'},
          {action: 'Edit', icon: '$edit', color: 'primary'},
        ],
        canAdd: true,
      },
      dialogConfig: {
        open: false,
        title: '',
        item: {},
        baseObject: new Project(),
        itemFields: [
          {name: 'name', label: 'Name', type: 'text', required: true},
          {name: 'status', label: 'Status', type: 'select', required: true, items: []},
          {name: 'startDate', label: 'Start Date', type: 'date', required: true},
          {name: 'endDate', label: 'End Date', type: 'date', required: true},
          {name: 'teamId', label: 'Team', type: 'select', required: true, items: []},
          {name: 'description', label: 'Description', type: 'text', required: false}
        ],
        detailTabs: [
          {title: 'Materials', component: 'ProjectMaterialsTable'},
        ]
      },
      filterConfig: {
        canSearch: true,
        canSortByWarehouse: false,
        canSortByTeam: false,
        selectedWarehouse: null
      }
    }
  },
  async mounted() {
    await this.initialize();
  },
  methods: {
    async initialize() {
      await this.fetchTeams();
      await this.fetchProjects();
      this.setSelectableStatuses();
    },
    async fetchProjects() {
      this.projects = await this.projectsService.asyncFindAll();

      this.projects.forEach(project => {
        project.teamName = this.getTeamNameById(project.teamId);
      });

      this.tableConfig.items = this.projects;
    },
    setSelectableStatuses() {
      this.dialogConfig.itemFields.find(field => field.name === 'status').items =
          Project.statusList.map(status => {
            return {name: status.displayName, id: status.value};
          });
    },
    async fetchTeams() {
      this.teams = await this.teamsService.asyncFindAll()
      this.dialogConfig.itemFields.find(field => field.name === 'teamId').items = this.teams;
    },
    async handleSave(item) {
      const savedItem = item.id ?
          await this.projectsService.asyncUpdate(item.id, item) :
          await this.projectsService.asyncAdd(item);
      if (savedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to save item");
      }
      await this.initialize();
    },
    async handleDelete(item) {
      const deletedItem = await this.projectsService.asyncDeleteById(item.id);
      if (deletedItem) {
        this.dialogConfig.open = false;
      } else {
        console.error("Failed to delete item");
      }
      await this.initialize();
    },
    getTeamNameById(teamId) {
      if (!teamId) return 'None';
      const team = this.teams.find(team => team.id === teamId);
      return team ? team.name : 'Unknown';
    }
  }
}
</script>

<style scoped>

</style>