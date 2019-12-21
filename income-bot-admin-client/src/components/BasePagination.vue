<template>
    <ul :class="[size && `pagination-${size}`, align && `justify-content-${align}`]" class="pagination">
        <li :class="{disabled: value === 1}" class="page-item prev-page">
            <a @click="prevPage" aria-label="Previous" class="page-link">
                <span aria-hidden="true"><i aria-hidden="true" class="fa fa-angle-left"></i></span>
            </a>
        </li>
        <li :class="{active: value === item}" :key="item"
            class="page-item"
            v-for="item in range(minPage, maxPage)">
            <a @click="changePage(item)" class="page-link">{{item}}</a>
        </li>
        <li :class="{disabled: value === totalPages}" class="page-item next-page">
            <a @click="nextPage" aria-label="Next" class="page-link">
                <span aria-hidden="true"><i aria-hidden="true" class="fa fa-angle-right"></i></span>
            </a>
        </li>
    </ul>
</template>
<script>
    export default {
        name: "base-pagination",
        props: {
            pageCount: {
                type: Number,
                default: 0,
                description:
                    "Pagination name count. This should be specified in combination with perPage"
            },
            perPage: {
                type: Number,
                default: 10,
                description:
                    "Pagination per name. Should be specified with total or pageCount"
            },
            total: {
                type: Number,
                default: 0,
                description:
                    "Can be specified instead of pageCount. The name count in this case will be total/perPage"
            },
            value: {
                type: Number,
                default: 1,
                description: "Pagination value"
            },
            size: {
                type: String,
                default: "",
                description: "Pagination size"
            },
            align: {
                type: String,
                default: "",
                description: "Pagination alignment (e.g center|start|end)"
            }
        },
        computed: {
            totalPages() {
                if (this.pageCount > 0) return this.pageCount;
                if (this.total > 0) {
                    return Math.ceil(this.total / this.perPage);
                }
                return 1;
            },
            pagesToDisplay() {
                if (this.totalPages > 0 && this.totalPages < this.defaultPagesToDisplay) {
                    return this.totalPages;
                }
                return this.defaultPagesToDisplay;
            },
            minPage() {
                if (this.value >= this.pagesToDisplay) {
                    const pagesToAdd = Math.floor(this.pagesToDisplay / 2);
                    const newMaxPage = pagesToAdd + this.value;
                    if (newMaxPage > this.totalPages) {
                        return this.totalPages - this.pagesToDisplay + 1;
                    }
                    return this.value - pagesToAdd;
                } else {
                    return 1;
                }
            },
            maxPage() {
                if (this.value >= this.pagesToDisplay) {
                    const pagesToAdd = Math.floor(this.pagesToDisplay / 2);
                    const newMaxPage = pagesToAdd + this.value;
                    if (newMaxPage < this.totalPages) {
                        return newMaxPage;
                    } else {
                        return this.totalPages;
                    }
                } else {
                    return this.pagesToDisplay;
                }
            }
        },
        data() {
            return {
                defaultPagesToDisplay: 5
            };
        },
        methods: {
            range(min, max) {
                let arr = [];
                for (let i = min; i <= max; i++) {
                    arr.push(i);
                }
                return arr;
            },
            changePage(item) {
                this.$emit("input", item);
            },
            nextPage() {
                if (this.value < this.totalPages) {
                    this.$emit("input", this.value + 1);
                }
            },
            prevPage() {
                if (this.value > 1) {
                    this.$emit("input", this.value - 1);
                }
            }
        },
        watch: {
            perPage() {
                this.$emit("input", 1);
            },
            total() {
                this.$emit("input", 1);
            }
        }
    };
</script>
