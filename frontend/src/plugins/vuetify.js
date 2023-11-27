import {createVuetify} from 'vuetify'
import {md3} from "vuetify/blueprints";
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import {aliases, mdi} from "vuetify/iconsets/mdi-svg";
import {
    mdiAccountCircleOutline,
    mdiAccountOutline,
    mdiArchiveOutline,
    mdiCancel,
    mdiCogOutline,
    mdiContentSaveOutline,
    mdiCrowd,
    mdiInformationOutline,
    mdiLogout, mdiMagnify,
    mdiNut,
    mdiPencilOutline,
    mdiSolarPowerVariantOutline,
    mdiTrashCanOutline,
    mdiViewDashboardOutline,
    mdiWarehouse
} from "@mdi/js";

const solarSedumLightTheme = {
    dark: false,
    colors: {
        background: '#f8f8f8',
        surface: '#ffffff',
        primary: '#c7d02c',
        'primary-darken-1': '#572700',
        secondary: '#333333',
        'secondary-darken-1': '#bfbfbf',
        error: '#B00020',
        info: '#2196F3',
        success: '#4CAF50',
        warning: '#FB8C00',
    }
}

const solarSedumDarkTheme = {
    dark: true,
    colors: {
        background: '#333333',
        surface: '#424242',
        primary: '#c7d02c',
        'primary-darken-1': '#572700',
        secondary: '#ffffff',
        'secondary-darken-1': '#bfbfbf',
        error: '#B00020',
        info: '#2196F3',
        success: '#4CAF50',
        warning: '#FB8C00',
    }
}

export default createVuetify({
    blueprint: md3,
    components,
    directives,
    theme: {
        defaultTheme: 'solarSedumLightTheme',
        themes: {
            solarSedumLightTheme,
            solarSedumDarkTheme,
        }
    },
    icons: {
        defaultSet: 'mdi',
        aliases: {
            ...aliases,
            edit: mdiPencilOutline,
            delete: mdiTrashCanOutline,
            save: mdiContentSaveOutline,
            cancel: mdiCancel,
            info: mdiInformationOutline,
            dashboard: mdiViewDashboardOutline,
            settings: mdiCogOutline,
            inventory: mdiArchiveOutline,
            team: mdiCrowd,
            warehouse: mdiWarehouse,
            product: mdiNut,
            project: mdiSolarPowerVariantOutline,
            user: mdiAccountOutline,
            logout: mdiLogout,
            account: mdiAccountCircleOutline,
            search: mdiMagnify
        },
        sets: {
            mdi
        }
    }
});