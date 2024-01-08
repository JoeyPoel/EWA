<template>
  <v-container>
    <v-data-table
        :headers="warehouseTeamHeaders"
        :items="warehouseTeams"
        :items-per-page-options="itemsPerPageOptions"
        :items-per-page="itemsPerPage"
        :show-expand="true"
        :sort-by="['name']"
        class="elevation-1"
        density="compact"
    >
      <template v-slot:expanded-row="{ item }">
        <v-chip-group>
          <v-chip v-for="member in item.teamMembers" :key="member.id" color="primary" variant="elevated">
            {{ member.name }}
          </v-chip>
        </v-chip-group>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>

export default {
  name: "WarehouseTeamsTable",
  inject: ['teamsService', 'usersService'],
  props: {
    item: {
      type: Object,
      required: false
    },
    warehouseId: {
      type: Number,
      required: false
    },
    itemsPerPage:{
      type: Number,
      required: false,
      default: 10
    },
    density:{
      type: String,
      required: false,
      default: 'comfortable'
    }
  },
  watch: {
    warehouseId(){
      this.loadWarehouseTeams();
    }
  },
  data() {
    return {
      warehouseTeams: [],
      itemsPerPageOptions: [5, 10],
      warehouseTeamHeaders: [
        {title: 'Name', value: 'name'},
        {title: 'Leader', value: 'teamLeader'},
        {title: 'Size', value: 'teamMembers.length'},
      ],
      teamMembersHeaders: [
        {title: 'Name', value: 'name'},
        {title: 'Email', value: 'email'},
        {title: 'Role', value: 'role'},
      ],
    }
  },
  async mounted() {
    await this.loadWarehouseTeams();
  },
  methods: {
    async loadWarehouseTeams() {
      let id = null;
      if (this.warehouseId) {
        id = this.warehouseId;
      } else if (this.item && this.item.id) {
        id = this.item.id;
      }

      this.warehouseTeams = id ? await this.teamsService.asyncFindAllByWarehouseId(id) :
          await this.teamsService.asyncFindAll();

      for (const team of this.warehouseTeams) {
        if (team.leaderId !== null) {
          team.teamLeader = await this.usersService.asyncGetById(team.leaderId);
        }
        if (team.teamMembers !== null) {
          team.teamMembers = await this.usersService.asyncFindAllByTeamId(team.id);
        }
      }
    },
  },
}
</script>