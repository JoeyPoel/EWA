import {createRouter, createWebHashHistory} from 'vue-router';
import warehouseInventoryComponent from "@/components/WarehouseInventoryComponent.vue";
// import WarehouseDetailComponent from "@/components/warehouses/WarehouseDetailComponent.vue";
import warehouseOverviewComponent from "@/components/warehouses/WarehouseOverviewComponent.vue";
import AdminProductsComponent from "@/components/AdminProductsComponent.vue";
import dashboardComponent from "@/components/DashboardComponent.vue";
import userComponent from "@/components/userComponent.vue";
import productComponent from "@/components/products/ProductComponent.vue";
// import projectComponent from "@/components/projects/ProjectComponent.vue";
import ProductDetail from "@/components/products/ProductDetail.vue";
import ProductAdd from "@/components/products/ProductAdd.vue";
import TeamComponent from "@/components/teams/TeamComponent";
import LoginComponent from "@/components/LoginComponent.vue";
import WarehouseDetailModalComponent from "@/components/warehouses/WarehouseModalComponent.vue";
import WarehouseAddModalComponent from "@/components/warehouses/WarehouseAddModalComponent.vue";
import ProjectListComponent from "@/components/projects/ProjectListComponent.vue";
import EditTeamModal from "@/components/teams/EditTeamModal";

const routes = [{
    path: '/warehouse', name: 'Warehouse', meta: {
        requiresAuth: true
    }, children: [{
        path: 'inventory',
        name: 'WarehouseComponent',
        component: warehouseInventoryComponent, // TODO: add navigation by id
    }, {
        path: 'overview', name: 'WarehouseOverviewComponent', component: warehouseOverviewComponent, children: [// {
            //     path: ':id',
            //     name: 'WarehouseDetailComponent',
            //     component: WarehouseDetailComponent
            // },
            {
                path: ':id', name: 'WarehouseDetailModalComponent', component: WarehouseDetailModalComponent
            }, {
                path: 'add', name: 'WarehouseAddComponent', component: WarehouseAddModalComponent
            }]
    }]
}, {
    path: '/product', name: 'Product', meta: {
        requiresAuth: true
    }, component: productComponent, children: [{
        path: ':id', name: 'ProductDetail', component: ProductDetail, props: true
    }, {
        path: 'add', name: 'ProductAdd', component: ProductAdd,
    }]
}, {
    path: '/project', name: 'Project', meta: {
        requiresAuth: true
    }, component: ProjectListComponent
}, {
    path: '/user', name: 'User', meta: {
        requiresAuth: true
    }, component: userComponent
}, {
    path: '/dashboard', name: 'Dashboard', meta: {
        requiresAuth: true
    }, component: dashboardComponent
}, /*{
        path: '/admin/products',
        name: 'AdminProducts',
        meta: {
            requiresAuth: true
        },
        component: AdminProductsComponent
    },*/
    /* {
        path: '/admin/project',
        name: 'AdminProject',
        component: ProjectListComponent
    },*/
    {
        path: '/admin', name: 'Admin', children: [{
            path: 'project', name: 'AdminProject', component: ProjectListComponent,
        }, {
            path: 'products', name: 'AdminProducts', component: AdminProductsComponent
        }]
    }, {
        path: '/teams', name: 'Teams', component: TeamComponent, meta: {
            requiresAuth: true
        }, children: [{
            path: ':id', name: 'EditTeamModal', component: EditTeamModal, props: true
        }, {
            path: '/teams/add', name: 'AddTeamModal', component: EditTeamModal, props: true
        }]
    }, {
        path: '/log-in', name: 'Login', meta: {
            hideNavbar: true
        }, component: LoginComponent
    }, {
        path: '/', redirect: '/log-in',
    },];

export const router = createRouter({
    history: createWebHashHistory(), routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!sessionStorage.getItem("email")) {
            next({name: 'Login'})
        } else {
            next()
        }
    } else {
        if (!sessionStorage.getItem("email")) {
            next()
        } else {
            next({name: 'Dashboard'})
        }
    }
})