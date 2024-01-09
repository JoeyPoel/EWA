import {createVuetify} from 'vuetify'
import {md3} from "vuetify/blueprints";
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import {aliases, mdi} from "vuetify/iconsets/mdi-svg";
import {
    mdiAccountCircleOutline,
    mdiAccountOutline,
    mdiArchiveOutline,
    mdiCancel, mdiClose,
    mdiCogOutline,
    mdiContentSaveOutline,
    mdiCrowd,
    mdiInformationOutline, mdiLink, mdiLinkPlus,
    mdiLogout,
    mdiMagnify,
    mdiNut, mdiOpenInNew,
    mdiPencilOutline, mdiPlus, mdiRestore,
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
        'primary-darken-1': '#b1b452',
        secondary: '#572700',
        'secondary-darken-1': '#4e2300',
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
        'primary-darken-1': '#b1b452',
        secondary: '#572700',
        'secondary-darken-1': '#4e2300',
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
            account: mdiAccountCircleOutline,
            cancel: mdiCancel,
            dashboard: mdiViewDashboardOutline,
            delete: mdiTrashCanOutline,
            edit: mdiPencilOutline,
            info: mdiInformationOutline,
            inventory: mdiArchiveOutline,
            logout: mdiLogout,
            product: mdiNut,
            project: mdiSolarPowerVariantOutline,
            save: mdiContentSaveOutline,
            search: mdiMagnify,
            settings: mdiCogOutline,
            team: mdiCrowd,
            user: mdiAccountOutline,
            warehouse: mdiWarehouse,
            link: mdiLink,
            link_plus: mdiLinkPlus,
            open_in_new: mdiOpenInNew,
            new: mdiPlus,
            close: mdiClose,
            restore: mdiRestore
        },
        sets: {
            mdi
        }
    }
});