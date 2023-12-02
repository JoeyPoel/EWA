import {createRouter, createWebHashHistory} from 'vue-router';

const routes = [
    {
        path: '/inventories',
        name: 'Inventories',
        component: () => import('@/views/admin/inventories/InventoriesComponent.vue'),
        meta: {
            requiresAuth: true
        },
    },
    {
        path: '/warehouses',
        name: 'Warehouses',
        component: () => import('@/views/admin/warehouses/WarehousesComponent.vue'),
        meta: {
            requiresAuth: true
        },
    },
    {
        path: '/products',
        name: 'Products',
        component: () => import('@/views/admin/products/ProductsComponent.vue'),
        meta: {
            requiresAuth: true
        },
    },
    {
        path: '/projects',
        name: 'Projects',
        component: () => import('@/views/admin/projects/ProjectsComponent.vue'),
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/users',
        name: 'Users',
        component: () => import('@/views/admin/users/UsersComponent.vue'),
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/admin/dashboard/DashboardComponent.vue'),
        meta: {
            requiresAuth: true
        },
    },
    {
        path: '/teams',
        name: 'Teams',
        component: () => import('@/views/admin/teams/TeamsComponent.vue'),
        meta: {
            requiresAuth: true
        },
    },
    {
        path: '/log-in',
        name: 'Login',
        component: () => import('@/components/LoginComponent.vue'),
        meta: {
            hideNavbar: true
        },
    },
    {
        path: '/', redirect: '/log-in',
    },
];

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