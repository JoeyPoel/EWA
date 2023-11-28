<template>
  <!--  TODO: Validate given props in script and throw adequate errors-->
  <!--  TODO: validate filter items in scripts-->
  <!--  TODO: Make header stick-able-->
  <v-data-table :headers="tHeaders" :items="tableItems">
    <template v-slot:top>
      <v-toolbar flat>
        <div v-if="tableTitle">
          <v-toolbar-title>{{ tableTitle }}</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
        </div>
        <div v-if="tableFilter">
          <v-select :items="tableFilter.items" :chips="true" :clearable="true" item-title="name" item-value="id"
                    :label="'Filter by ' + tableFilter.label" @change="handleFilterChange" style="min-width: 250px;"
          ></v-select>
        </div>
        <v-dialog v-if="canAdd || canEdit" v-model="dialogNew" max-width="800px">
          <template #activator="{ props }">
            <v-btn v-if="canAdd" class="mb-2" color="primary" dark v-bind="props"
                   @click="formTitle = `New ${this.modelName}`">
              New {{ modelName }}
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-form>
                  <!--                  TODO: Add validation rules-->
                  <v-row v-for="field in modelEditFields" :key="field.field" cols="12" md="4" sm="6">
                    <v-text-field v-if="field.type === 'text'" v-model="editedItem[field.field]" :label="field.field">
                    </v-text-field>
                    <v-text-field v-if="field.type === 'number'" v-model="editedItem[field.field]" :label="field.field"
                                  type="number"></v-text-field>
                    <v-text-field v-if="field.type === 'date'" v-model="editedItem[field.field]" :label="field.field"
                                  type="date"></v-text-field>
                    <v-autocomplete v-if="field.type === 'select'" v-model="editedItem[field.field]"
                                    :items="field.items" :label="field.field"></v-autocomplete>
                    <v-autocomplete v-if="field.type === 'multipleSelect'" v-model="editedItem[field.field]"
                                    :items="field.items" :label="field.field" chips="true" multiple="true">
                    </v-autocomplete>
                    <v-checkbox v-if="field.type === 'checkbox'" v-model="editedItem[field.field]" :label="field.field">
                    </v-checkbox>
                    <v-radio-group v-if="field.type === 'radio'" v-model="editedItem[field.field]" :items="field.items"
                                   :label="field.field">
                      <v-radio v-for="item in field.items" :key="item.value" :label="item.title" :value="item.value">
                      </v-radio>
                    </v-radio-group>
                    <v-textarea v-if="field.type === 'textarea'" v-model="editedItem[field.field]" :label="field.field">
                    </v-textarea>
                  </v-row>
                </v-form>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue-darken-1" variant="text" @click="closeNew">Cancel</v-btn>
              <v-btn color="blue-darken-1" variant="text" @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDetails" max-width="800px">
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>
            <v-spacer></v-spacer>
            <v-card-text>
              <v-list lines="two">
                <v-list-item v-for="detail in modelDetails" v-bind:key="detail.field" v-model="this.editedItem" :title="detail.label"
                             active>
                  <template v-slot:subtitle>
                    <p v-if="detail.type === 'text'">{{ editedWarehouse[detail.field] }}</p>
                    <p v-if="detail.type === 'number'">{{ editedWarehouse[detail.field] }}</p>
                    <p v-if="detail.type === 'date'">{{ editedWarehouse[detail.field] }}</p>
                    <div v-if="detail.type === 'list'">
                      <v-chip v-for="item in detail.items" :key="item.value" :label="item.title" :value="item.value">
                      </v-chip>
                    </div>
                  </template>
                </v-list-item>
              </v-list>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue-darken-1" variant="text" @click="closeDetails">Close</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="800px">
          <v-card>
            <v-card-title class="text-h5">Are you sure you want to delete this {{ modelName }}?
            </v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <!--    TODO : Make icons lighter unless hovered over-->
    <template v-if="hasActions" v-slot:[`item.actions`]="{ item }">
      <v-icon v-if="canDetail" class="me-2" size="small" @click="seeDetails(item)"> $info</v-icon>
      <v-icon v-if="canEdit" class="me-2" size="small" @click="editItem(item)"> $edit</v-icon>
      <v-icon v-if="canDelete" size="small" @click="deleteItem(item)"> $delete</v-icon>
    </template>
    <template v-slot:no-data>
      <v-card>
        <v-card-title class="text-h5">No {{ tableTitle }} found</v-card-title>
      </v-card>
    </template>
  </v-data-table>
</template>


<script>
export default {
  name: "BasteTable",
  props: {
    modelName: {
      type: String,
      required: true,
    },
    tableTitle: {
      type: String,
      required: false,
    },
    tableHeaders: {
      type: Array(Object),
      required: true,
    },
    tableItems: {
      type: Array(Object),
      required: true,
    },
    tableActions: {
      type: Array(String),
      required: false,
    },
    modelDetails: {
      type: Array(Object),
      required: false,
      // type: [{
      //   field: undefined,
      //   label: String,
      //   type: ("text" || "number" || "date" || "list"),
      //   icon: String,
      //   items: [{
      //     value: undefined,
      //     title: String
      //   }],
      // }],
    },
    modelEditFields: {
      type: Array(Object),
      required: false,
      // type: [{
      //   field: undefined,
      //   fieldLabel: String,
      //   inputType: ("text" || "number" || "date" || "select" || "multipleSelect" || "checkbox" ||
      //       "radio" || "textarea"),
      //   required: Boolean,
      //   disabled: Boolean,
      //   items: [{
      //     value: undefined,
      //     title: String
      //   }],
      // }],
    },
    tableFilter: {
      type: Array(Object),
      required: false,
      // type: {
      //   label: String,
      //   items: [{
      //     id: Number,
      //     name: String
      //   }],
      // },
    },
  },
  data: () => ({
    dialogNew: false,
    dialogDelete: false,
    dialogDetails: false,
    //TODO: Add Icons
    headers: [],
    editedIndex: -1,
    editedItem: {},
    defaultItem: {},
    formTitle: '',
  }),

  computed: {
    isFilteredList() {
      return this.tableFilter && this.tableFilter.items.length > 0;
    },
    hasActions() {
      return this.tableActions && this.tableActions.length > 0;
    },
    canAdd() {
      return this.tableActions && this.tableActions.includes('new');
    },
    canEdit() {
      return this.tableActions && this.tableActions.includes('edit');
    },
    canDelete() {
      return this.tableActions && this.tableActions.includes('delete');
    },
    canDetail() {
      return this.tableActions && this.tableActions.includes('detail');
    },
    tHeaders() {
      let h = [...this.tableHeaders];
      if (this.tableActions && this.tableActions.length > 0) {
        h.push({title: 'Actions', key: 'actions', sortable: false})
      }
      return h;
    },
    tFilterItems() {
      if (!this.tableFilter) {
        return [];
      }
      return [...this.tableFilter.items];
    },
  },

  mounted() {
    console.log(this.tableFilter)
  },

  watch: {
    dialogNew(val) {
      val || this.closeNew()
    },
    dialogDelete(val) {
      val || this.closeDelete()
    },

  },

  methods: {
    handleFilterChange() {
      //   TODO: Handle filter change
      console.log('Filter changed. Still need to implement this.')
      console.log(this.tableFilter)
    },

    seeDetails(item) {
      this.formTitle = `${this.modelName} Details`;
      this.editedIndex = this.tableItems.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogDetails = true
    },

    editItem(item) {
      this.formTitle = `Edit ${this.modelName}`;
      this.editedIndex = this.tableItems.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogNew = true
    },

    deleteItem(item) {
      this.formTitle = `Delete ${this.modelName}`;
      this.editedIndex = this.tableItems.indexOf(item)
      this.editedItem = {}
      this.dialogDelete = true
    },

    deleteItemConfirm() {
      this.$emit('delete-item', this.editedItem)
      // this.tableItems.splice(this.editedIndex, 1)
      this.closeDelete()
    },

    closeDetails() {
      this.editedItem = {}
      this.dialogDetails = false
    },

    closeNew() {
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
      this.dialogNew = false
    },

    closeDelete() {
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (this.editedIndex > -1) {
        this.$emit('edit-item', this.editedItem)
        Object.assign(this.tableItems[this.editedIndex], this.editedItem)
      } else {
        this.$emit('add-item', this.editedItem)
        // this.tableItems.push(this.editedWarehouse)
      }
      this.closeNew()
    },
  },
}
</script>

<style scoped>

</style>