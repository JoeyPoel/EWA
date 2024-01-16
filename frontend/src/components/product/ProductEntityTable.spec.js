import { mount } from '@vue/test-utils';
import { reactive } from 'vue';
import ProjectEntityTable from "@/components/product/ProductEntityTable.vue";

/**
 * Tests for Project Entity Table
 * @Author Jayden Gunhan
 */
describe('ProductEntityTable', () => {
    let wrapper;

    beforeEach(async () => {
        const productsServiceMock = {
            asyncFindAll: jest.fn().mockResolvedValue([]),
            asyncUpdate: jest.fn().mockResolvedValue(null),
            asyncAdd: jest.fn().mockResolvedValue(null),
            asyncDeleteById: jest.fn().mockResolvedValue(null),
            initialize: jest.fn(),
        };

        wrapper = mount(ProjectEntityTable, {
            global: {
                provide: {
                    projectsService: reactive(productsServiceMock)
                },
            },
        });

        await wrapper.vm.$nextTick();
    });
});