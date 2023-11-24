export class Model {
    static equals(a, b) {
        if (Object.keys(a).length === Object.keys(b).length) {
            for (const key in a) {
                if (a[key] !== b[key]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    static copy(toBeCopied) {
        return Object.assign(new this, toBeCopied);
    }

    static fromJson(json) {
        return Object.assign(new this, json);
    }

    static getNew() {
        return Object.assign({}, new this);
    }
}