<template>
    <table :class="tableClass" class="table tablesorter">
        <thead :class="theadClasses">
        <tr>
            <slot :columns="columns" name="columns">
                <th :key="column" v-for="column in columns">{{ column }}</th>
            </slot>
        </tr>
        </thead>
        <tbody :class="tbodyClasses">
        <tr :key="index" v-for="(item, index) in data">
            <slot :index="index" :row="item">
                <td
                        :key="index"
                        v-for="(column, index) in colsWithValue(item)">
                    {{ itemValue(item, column) }}
                </td>
            </slot>
        </tr>
        </tbody>
    </table>
</template>
<script>
    export default {
        name: 'base-table',
        props: {
            columns: {
                type: Array,
                default: () => [],
                description: 'Table columns'
            },
            data: {
                type: Array,
                default: () => [],
                description: 'Table data'
            },
            type: {
                type: String, // striped | hover
                default: '',
                description: 'Whether table is striped or hover type'
            },
            theadClasses: {
                type: String,
                default: '',
                description: '<thead> css classes'
            },
            tbodyClasses: {
                type: String,
                default: '',
                description: '<tbody> css classes'
            }
        },
        computed: {
            tableClass() {
                return this.type && `table-${this.type}`;
            },
            colsWithValue() {
                return (row) => {
                    return this.columns.filter(column => this.hasValue(row, column))
                }
            }
        },
        methods: {
            hasValue(item, column) {
                return item[column.toLowerCase()] !== 'undefined';
            },
            itemValue(item, column) {
                return item[column.toLowerCase()];
            }
        }
    };
</script>
<style></style>
