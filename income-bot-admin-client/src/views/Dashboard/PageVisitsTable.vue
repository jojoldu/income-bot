<template>
    <div class="card">
        <div class="card-header border-0">
            <div class="row align-items-center">
                <div class="col">
                    <h3 class="mb-0">{{title}}별 지표</h3>
                </div>
            </div>
        </div>

        <div class="table-responsive">
            <base-table :data="tableData"
                        thead-classes="thead-light">
                <template slot="columns">
                    <th>{{title}}명</th>
                    <th>순위</th>
                    <th>판매지수</th>
                    <th>Bounce rate</th>
                </template>

                <template slot-scope="{row}">
                    <th scope="row">
                        {{row.page}}
                    </th>
                    <td>
                        {{row.visitors}}
                    </td>
                    <td>
                        {{row.unique}}
                    </td>
                    <td>
                        <i :class="row.bounceRateDirection === 'up' ? 'text-success': 'text-danger'"
                           class="fas fa-arrow-up text-success mr-3">
                        </i>
                        {{row.bounceRate}}
                    </td>
                </template>

            </base-table>
        </div>

    </div>
</template>
<script>
    export default {
        name: 'page-visits-table',
        data() {
            return {
                title: '출판사',
                tableData: [
                    {
                        page: '/argon/',
                        visitors: '4,569',
                        unique: '340',
                        bounceRate: '46,53%',
                        bounceRateDirection: 'up'
                    },
                    {
                        page: '/argon/index.html',
                        visitors: '3,985',
                        unique: '319',
                        bounceRate: '46,53%',
                        bounceRateDirection: 'down'
                    },
                    {
                        page: '/argon/charts.html',
                        visitors: '3,513',
                        unique: '294',
                        bounceRate: '36,49%',
                        bounceRateDirection: 'down'
                    },
                    {
                        page: '/argon/tables.html',
                        visitors: '2,050',
                        unique: '147',
                        bounceRate: '50,87%',
                        bounceRateDirection: 'up'
                    },
                    {
                        page: '/argon/profile.html',
                        visitors: '1,795',
                        unique: '190',
                        bounceRate: '46,53%',
                        bounceRateDirection: 'down'
                    }
                ]
            }
        }
    }
</script>
<style>
</style>
