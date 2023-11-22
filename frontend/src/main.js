import App from './App.vue';
import { createApp } from 'vue';

import { router } from "@/router";
import {IconsPlugin, BootstrapVue} from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.js';

createApp(App).use(router, BootstrapVue, IconsPlugin).mount('#app')
