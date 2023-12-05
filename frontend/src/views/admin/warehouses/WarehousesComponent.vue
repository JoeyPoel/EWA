<template>
  <v-container fluid>
    <base-card class="mt-1" color="secondary" title="Warehouses">
      <v-row>
        <v-col>
          <v-text-field v-model="search" label="Search Warehouse" prepend-inner-icon="$search" variant="outlined"/>
        </v-col>
      </v-row>
      <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="warehouses"
          :search="search"
          class="elevation-1"
          item-value="id">
        <template v-slot:top>
          <v-toolbar flat>
            <v-dialog v-model="dialogNew" max-width="800px">
              <template v-slot:activator="{ props }">
                <v-spacer></v-spacer>
                <v-btn class="mb-2" color="secundary" dark v-bind="props">New Warehouse</v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h5">New Warehouse</v-card-title>
                <v-card-text>
                  <v-form>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.name" label="Name" type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.address" label="Address" type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.location" label="Location"
                                    type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.postcode" label="Postcode"
                                    type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.country" label="Country" type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.contactName" label="Contact Name"
                                    type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.contactEmail" label="Contact Email"
                                    type="text"></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field v-model="editedWarehouse.contactPhone" label="Contact Phone"
                                    type="text"></v-text-field>
                    </v-row>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue-darken-1" variant="text" @click="saveNew">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogEdit" max-width="800px">
              <v-card>
                <v-tabs v-model="tab" bg-color="transparent">
                  <v-tab value="details">Details</v-tab>
                  <v-tab value="capacity">Storage Capacity</v-tab>
                </v-tabs>
                <v-card-text>
                  <v-window v-model="tab">
                    <v-window-item value="details">
                      <v-form>
                        <v-col>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.name" label="Name" type="text"></v-text-field>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.location" label="Location"
                                          type="text"></v-text-field>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.postcode" label="Postcode"
                                          type="text"></v-text-field>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.country" label="Country" type="text"></v-text-field>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.contactName" label="Contact Name"
                                          type="text"></v-text-field>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.contactEmail" label="Contact Email"
                                          type="text"></v-text-field>
                          </v-row>
                          <v-row>
                            <v-text-field v-model="editedWarehouse.contactPhone" label="Contact Phone"
                                          type="text"></v-text-field>
                          </v-row>
                        </v-col>
                      </v-form>
                    </v-window-item>
                    <v-window-item value="capacity">
                      <v-data-table
                          :headers="warehouseCapacityHeaders"
                          :items="warehouseProductCategoryCapacities"
                          :search="warehouseCapacitySearch"
                          class="elevation-1">
                        <template v-slot:top>
                          <v-toolbar flat>
                            <v-toolbar-title>Warehouse Capacity By Product Category</v-toolbar-title>
                            <v-dialog v-model="dialogEditCapacity" max-width="800px">
                              <v-card>
                                <v-card-title><span class="text-h5"> Edit Capacity</span></v-card-title>
                                <v-card-text>
                                  <v-container>
                                    <v-row>
                                      <v-form>
                                        <v-col>
                                          <v-row>
                                            <span class="text-h6">Category Name</span>
                                            <span class="text-h6">{{
                                                editedWarehouseCapacityObject.productCategoryName
                                              }}</span>
                                          </v-row>
                                          <v-row>
                                            <v-text-field v-model="editedWarehouseCapacityObject.minimumStockLevel"
                                                          label="Minimum Stock Level" type="number"></v-text-field>
                                          </v-row>
                                          <v-row>
                                            <v-text-field v-model="editedWarehouseCapacityObject.capacity"
                                                          label="Capacity"
                                                          type="number"></v-text-field>
                                          </v-row>
                                        </v-col>
                                      </v-form>
                                    </v-row>
                                  </v-container>
                                </v-card-text>
                              </v-card>
                            </v-dialog>
                          </v-toolbar>
                        </template>
                        <template v-slot:[`item.actions`]="{ capacity }">
                          <v-icon class="me-2" size="small" @click="editCapacity(capacity)">$edit</v-icon>
                        </template>
                      </v-data-table>
                    </v-window-item>
                  </v-window>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue-darken-1" variant="text" @click="saveCapacity">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDelete" max-width="800px">
              <v-card>
                <v-card-title class="text-h5">Are you sure you want to delete this warehouse?</v-card-title>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue-darken-1" variant="text" @click="close">Cancel</v-btn>
                  <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
                  <v-spacer></v-spacer>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDetails" max-width="800px">
              <v-card>
                <v-tabs v-model="tab" bg-color="transparent">
                  <v-tab value="details">Details</v-tab>
<!--                  <v-tab value="capacity">Storage Capacity</v-tab>-->
                  <v-tab value="teams">Teams</v-tab>
                  <v-tab value="projects">Projects</v-tab>
                  <v-tab value="transactions">Transactions</v-tab>
                  <!--                  <v-tab value="orders">Orders</v-tab>-->
                  <v-tab value="products">Products In Inventory</v-tab>
                </v-tabs>
                <v-card-text>
                  <v-window v-model="tab">
                    <v-window-item value="details">
                      <v-row>
                        <v-col>
                          <v-list>
                            <v-list-item>
                              <v-list-item-title>Warehouse Name</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.name }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Location</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.location }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Postcode</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.postcode }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Country</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.country }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Contact Name</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.contactName }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Contact Email</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.contactEmail }}</v-list-item-subtitle>
                            </v-list-item>
                            <v-list-item>
                              <v-list-item-title>Contact Phone</v-list-item-title>
                              <v-list-item-subtitle>{{ selectedWarehouse.contactPhone }}</v-list-item-subtitle>
                            </v-list-item>
                          </v-list>
                        </v-col>
                      </v-row>
                    </v-window-item>
<!--                    <v-window-item value="capacity">-->
<!--                      <v-data-table-->
<!--                          :headers="warehouseDetailsCapacityHeaders"-->
<!--                          :items="warehouseProductCategoryCapacities"-->
<!--                          :search="warehouseCapacitySearch"-->
<!--                          :sort-by="['productCategoryName']"-->
<!--                          :items-per-page-options="detailTableItemsPerPageOptions"-->
<!--                          class="elevation-1">-->
<!--                      </v-data-table>-->
<!--                    </v-window-item>-->
                    <v-window-item value="teams">
                      <v-data-table
                          :headers="warehouseTeamHeaders"
                          :items="warehouseTeams"
                          :search="warehouseTeamSearch"
                          :sort-by="['name']"
                          :items-per-page-options="detailTableItemsPerPageOptions"
                          class="elevation-1"
                          show-expand>
                        <template v-slot:expanded-row="{ item }">
                          <v-table>
                            <thead>
                            <tr>
                              <th class="text-left">Name</th>
                              <th class="text-left">Email</th>
                              <th class="text-left">Role</th>
                              <th class="text-left">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="member in item.teamMembers" :key="member.id">
                              <td>{{ member.name }}</td>
                              <td>{{ member.email }}</td>
                              <td>{{ member.role }}</td>
                              <td>
                                <v-icon class="me-2" size="small" @click="goToEditTeamMember(member)">
                                  $edit
                                </v-icon>
                              </td>
                            </tr>
                            </tbody>
                          </v-table>
                        </template>
                        <template v-slot:[`item.actions`]="{ item }">
                          <v-icon class="me-2" size="small" @click="goToEditTeam(item)">$edit</v-icon>
                        </template>
                      </v-data-table>
                    </v-window-item>
                    <v-window-item value="projects">
                      <v-data-table
                          :headers="warehouseProjectHeaders"
                          :items="warehouseProjects"
                          :search="warehouseProjectSearch"
                          :sort-by="['startDate']"
                          :items-per-page-options="detailTableItemsPerPageOptions"
                          class="elevation-1">
                        <template v-slot:[`item.actions`]="{ item }">
                          <v-icon class="me-2" size="small" @click="goToEditProject(item)">$edit</v-icon>
                        </template>
                      </v-data-table>
                    </v-window-item>
                    <v-window-item value="transactions">
                      <v-data-table
                          :headers="warehouseTransactionHeaders"
                          :items="warehouseTransactions"
                          :items-per-page-options="detailTableItemsPerPageOptions"
                          :search="warehouseTransactionSearch"
                          :sort-by="['date']"
                          class="elevation-1">
                        <template v-slot:[`item.transactionCategory`]="{ value }">
                          <v-chip :color="getColor(value)">
                            {{ value }}
                          </v-chip>
                        </template>
                        <template v-slot:[`item.actions`]="{ item }">
                          <v-icon class="me-2" size="small" @click="goToEditTransaction(item)">$edit</v-icon>
                        </template>
                      </v-data-table>
                    </v-window-item>
                    <!--                    <v-window-item value="orders" @click="loadWarehouseOrders">-->
                    <!--                      <v-card>-->
                    <!--                        <v-card-title class="text-h5">Orders</v-card-title>-->
                    <!--                        <v-card-text>-->
                    <!--                          <v-container>-->
                    <!--                            <v-row>-->
                    <!--                              <v-col>-->
                    <!--                                <v-data-table-->
                    <!--                                    :headers="warehouseOrderHeaders"-->
                    <!--                                    :items="warehouseOrders"-->
                    <!--                                    :search="warehouseOrderSearch"-->
                    <!--                                    :sort-by="['date']"-->
                    <!--                                    class="elevation-1">-->
                    <!--                                  <template v-slot:[`item.productName`]="{ item }">-->
                    <!--                                    <v-menu offset-y>-->
                    <!--                                      <template v-slot:activator="{ on, attrs }">-->
                    <!--                                        <v-btn small text v-bind="attrs" v-on="on">-->
                    <!--                                          {{ item.productName }}-->
                    <!--                                        </v-btn>-->
                    <!--                                      </template>-->
                    <!--                                      <v-list>-->
                    <!--                                        <v-list-item v-for="product in item.products" :key="product.id">-->
                    <!--                                          <v-list-item-title>-->
                    <!--                                            {{ product.name }} ({{ product.quantity }})-->
                    <!--                                          </v-list-item-title>-->
                    <!--                                        </v-list-item>-->
                    <!--                                      </v-list>-->
                    <!--                                    </v-menu>-->
                    <!--                                  </template>-->
                    <!--                                  <template v-slot:[`item.actions`]="{ item }">-->
                    <!--                                    <v-icon class="me-2" size="small" @click="goToEditOrder(item)">$edit</v-icon>-->
                    <!--                                  </template>-->
                    <!--                                </v-data-table>-->
                    <!--                              </v-col>-->
                    <!--                            </v-row>-->
                    <!--                          </v-container>-->
                    <!--                        </v-card-text>-->
                    <!--                      </v-card>-->
                    <!--                    </v-window-item>-->
                    <v-window-item value="products">
                      <v-data-table
                          :headers="warehouseProductHeaders"
                          :items="warehouseProducts"
                          :items-per-page-options="[5, 10]"
                          :search="warehouseProductSearch"
                          :sort-by="['name']"
                          class="elevation-1">
                        <template v-slot:[`item.actions`]="{ item }">
                          <v-icon class="me-2" size="small" @click="goToEditProduct(item)">$edit</v-icon>
                        </template>
                      </v-data-table>
                    </v-window-item>
                  </v-window>
                </v-card-text>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon class="me-2" size="small" @click="seeDetails(item)">$info</v-icon>
          <v-icon class="me-2" size="small" @click="editItem(item)">$edit</v-icon>
          <v-icon size="small" @click="deleteItem(item)">$delete</v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="secondary" @click="initialize">Reset</v-btn>
        </template>
      </v-data-table>
    </base-card>
  </v-container>
</template>

<script>
import BaseCard from "@/components/base/BaseCard.vue";
import {Warehouse} from "@/models/Warehouse";
import {Capacity} from "@/models/Capacity";

export default {
  name: "WarehousesComponent",
  components: {BaseCard},
  inject: ["warehousesService", "inventoryService", "usersService", "transactionsService", "teamsService",
    "projectsService"],
  data() {
    return {
      search: "",
      dialogNew: false,
      dialogEdit: false,
      dialogDelete: false,
      dialogDetails: false,
      detailTableItemsPerPageOptions: [5, 10],
      headers: [
        {title: "ID", value: "id"},
        {title: "Name", value: "name"},
        {title: "Location", value: "location"},
        {title: "Contact Name", value: "contactName"},
        {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseCapacityHeaders: [
        {title: "Category Name", value: "categoryName"},
        {title: "Minimum Stock Level", value: "minimumStockLevel"},
        {title: "Capacity", value: "capacity"},
        {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseDetailsCapacityHeaders: [
        {title: "Category Name", value: "productCategoryName"},
        {title: "Minimum Stock Level", value: "minimumStockLevel"},
        {title: "Capacity", value: "capacity"},
      ],
      warehouseTeamHeaders: [
        {title: "Team Name", value: "name"},
        {title: "Team size", value: "membersIds.length"},
        //   TODO: add team size. Show teammembers in a dropdown
        // {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseTeamMembersHeaders: [
        {title: "Name", value: "name"},
        {title: "Email", value: "email"},
        {title: "Role", value: "role"},
        // {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseProjectHeaders: [
        {title: "Project Name", value: "name"},
        {title: "Location", value: "location"},
        {title: "Start Date", value: "startDate"},
        {title: "End Date", value: "endDate"},
        {title: "Status", value: "status"},
        // {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseTransactionHeaders: [
        {title: "Date", value: "transactionDate"},
        {title: "Product ID", value: "productId"},
        {title: "Quantity", value: "quantity"},
        {title: "Transaction Type", value: "transactionType"},
        // {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseOrderHeaders: [
        {title: "Date", value: "date"},
        {title: "Product Name", value: "productName"},
        {title: "Quantity", value: "quantity"},
        {title: "Order Status", value: "orderStatus"},
        // {title: "Actions", value: "actions", sortable: false}
      ],
      warehouseProductHeaders: [
        {title: "Product Name", value: "name"},
        {title: "Quantity", value: "quantity"},
        // {title: "Actions", value: "actions", sortable: false}
      ],
      selectedWarehouse: new Warehouse(),
      editedWarehouse: new Warehouse(),
      editedWarehouseCapacityObject: new Capacity(),
      defaultItem: new Warehouse(),
      defaultCapacityItem: new Capacity(),
      warehouseProductCategoryCapacities: [],
      warehouseTeams: [],
      warehouseProjects: [],
      warehouseTransactions: [],
      warehouseOrders: [],
      warehouseProducts: [],
      warehouseCapacitySearch: "",
      warehouseTeamSearch: "",
      warehouseProjectSearch: "",
      warehouseTransactionSearch: "",
      // warehouseOrderSearch: "",
      warehouseProductSearch: "",

      tab: "details",
      itemsPerPage: 10,
      warehouses: [],
      editedIndex: -1,
      editedCapacityIndex: -1,
      editedTeamIndex: -1,
    }
  },
  watch: {
    dialogNew(val) {
      val || this.close();
    },
    async dialogEdit(val) {
      if (!val) {
        this.close();
        return;
      }
      await this.loadWarehouseDetails();
      await this.loadWarehouseCapacity();
    },
    dialogDelete(val) {
      val || this.close();
    },
    async dialogDetails(val) {
      if (!val) {
        this.close();
        return;
      }
      await this.loadWarehouseDetails();
      await this.loadWarehouseCapacity();
      await this.loadWarehouseTeams();
      await this.loadWarehouseProjects();
      await this.loadWarehouseTransactions();
      // this.loadWarehouseOrders();
      await this.loadWarehouseProducts();
    },
  },
  async mounted() {
    await this.loadWarehouses();
  },
  methods: {
    async loadWarehouses() {
      this.warehouses = await this.warehousesService.asyncGetAll();
    },

    async loadWarehouseDetails() {
      this.selectedWarehouse = await this.warehousesService.asyncGetById(this.selectedWarehouse.id);
    },

    async loadWarehouseCapacity() {
      this.warehouseProductCategoryCapacities =
          await this.warehousesService.asyncGetCapacity(this.selectedWarehouse.id);
    },

    async loadWarehouseTeams() {
      this.warehouseTeams = await this.teamsService.asyncGetAllByWarehouseId(this.selectedWarehouse.id);
      for (const team of this.warehouseTeams) {
        if (team.leaderId !== null) {
          team.teamLeader = await this.usersService.asyncGetById(team.leaderId);
        }
        if (team.teamMembers !== null) {
          team.teamMembers = await this.usersService.asyncGetAllByTeamId(team.id);
        }
      }
      console.log(this.warehouseTeams);
    },

    async loadWarehouseProjects() {
      this.warehouseProjects = await this.projectsService.asyncGetAllByWarehouseId(this.selectedWarehouse.id);
    },

    async loadWarehouseTransactions() {
      this.warehouseTransactions = await this.transactionsService.asyncGetAllByWarehouseId(this.selectedWarehouse.id);
    },

    // async loadWarehouseOrders() {
    //   this.warehouseOrders = await this.ordersService.asyncGetAllByWarehouseId(this.selectedWarehouse.id);
    // },

    async loadWarehouseProducts() {
      this.warehouseProducts = await this.inventoryService.asyncGetAllByWarehouseId(this.selectedWarehouse.id);
    },

    goToEditCapacity(capacity) {
      console.log("(Yet to implement) Now editing capacity: " + capacity.id);
    },

    goToEditTeam(team) {
      console.log("(Yet to implement) Now editing team: " + team.id);
    },

    goToEditProject(project) {
      console.log("(Yet to implement) Now editing project: " + project.id);
    },

    goToEditTransaction(transaction) {
      console.log("(Yet to implement) Now editing transaction: " + transaction.id);
    },

    goToEditOrder(order) {
      console.log("(Yet to implement) Now editing order: " + order.id);
    },

    goToEditProduct(product) {
      console.log("(Yet to implement) Now editing product: " + product.id);
    },

    goToEditTeamMember(teamMember) {
      console.log("(Yet to implement) Now editing team member: " + teamMember.id);
    },

    initialize() {
      this.warehouses = this.items;
    },

    seeDetails(item) {
      this.selectedWarehouse = item;
      this.dialogDetails = true;
    },

    editItem(warehouse) {
      this.editedWarehouse = Object.assign({}, warehouse);
      this.selectedWarehouse = Object.assign({}, warehouse);
      this.dialogEdit = true;
    },

    async editCapacity(item) {
      this.editedCapacityIndex = this.warehouseProductCategoryCapacities.indexOf(item);
      await this.warehousesService.asyncUpdateCapacityById(this.editedWarehouseCapacityObject.id,
          this.editedWarehouseCapacityObject);

      this.editedWarehouseCapacityObject = Object.assign({}, item);
    },
    async close() {
      this.dialogNew = false;
      this.dialogEdit = false;
      this.dialogDelete = false;
      this.editedIndex = -1;
      this.editedCapacityIndex = -1;
      this.editedTeamIndex = -1;
      this.editedWarehouse = new Warehouse();
      this.selectedWarehouse = new Warehouse();
      await this.loadWarehouses();
    },

    deleteItem(item) {
      this.editedIndex = this.warehouses.indexOf(item);
      this.editedWarehouse = Object.assign(new Warehouse(), item);
      this.dialogDelete = true;
    },
    async deleteItemConfirm() {
      await this.warehousesService.asyncDeleteById(this.editedWarehouse.id);
      await this.close();
    },
    async saveNew() {
      await this.warehousesService.asyncAdd(this.editedWarehouse);
      this.close();
    },
    saveCapacity() {
      if (this.editedCapacityIndex > -1) {
        Object.assign(this.warehouseProductCategoryCapacities[this.editedCapacityIndex], this.editedWarehouseCapacityObject);
      } else {
        this.warehouseProductCategoryCapacities.push(this.editedWarehouseCapacityObject);
      }
      this.close();
    }
  },
}

</script>

<style scoped>

</style>