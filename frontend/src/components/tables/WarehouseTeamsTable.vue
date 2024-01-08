<template>
  <v-container>
    <v-data-table
        :headers="warehouseTeamHeaders"
        :items="warehouseTeams"
        :sort-by="['name']"
        :items-per-page-options="itemsPerPageOptions"
        class="elevation-1"
        :show-expand="true"
    >
      <template v-slot:expanded-row="{ item }">
        <v-chip-group>
          <v-chip v-for="member in item.teamMembers" :key="member.id" variant="elevated" color="primary">
            {{ member.name }}
          </v-chip>
        </v-chip-group>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
export default {
  name: "WarehouseStockLevelsTable",
  inject: ['teamsService', 'usersService'],
  props: {
    item:{
      type: Object,
      required: true
    },
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
      this.warehouseTeams = await this.teamsService.asyncFindAllByWarehouseId(this.item.id);
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