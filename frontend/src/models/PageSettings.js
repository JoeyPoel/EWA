export class PageSettings {
        constructor(page, elementsPerPage, direction, key) {
            this.page = page - 1;
            this.elementsPerPage = elementsPerPage;
            this.direction = direction ? direction : "ASC";
            this.key = key ? key : "id";
        }
 }