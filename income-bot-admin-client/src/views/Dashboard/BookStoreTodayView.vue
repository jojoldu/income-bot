<template>
    <div class="card">
        <div class="card-header border-0">
            <div class="row align-items-center">
                <div class="col">
                    <h3 class="mb-0">출판사별 지표 </h3>
                    <p>{{title}}</p>
                </div>
            </div>
        </div>

        <div class="table-responsive">
            <base-table :data="bookStores"
                        thead-classes="thead-light">
                <template slot="columns">
                    <th>출판사</th>
                    <th>순위</th>
                    <th>판매지수</th>
                    <th>증감률</th>
                </template>

                <template slot-scope="{row}">
                    <th scope="row">
                        {{row.name}}
                    </th>
                    <td>
                        {{row.salesPoint}}
                    </td>
                    <td>
                        {{row.rank}}
                    </td>
                    <td>
                        <i :class="row.bounceRateDirection === 'up' ? 'text-success': 'text-primary'"
                           class="fas fa-arrow-up mr-3">
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
        name: 'name-visits-table',
        data() {
            return {
                title: '스프링 부트와 AWS로 혼자 구현하는 웹 서비스',
                bookStores: [
                    {
                        name: '/argon/',
                        rank: '340',
                        salesPoint: '4,569',
                        bounceRate: '46,53%',
                        bounceRateDirection: 'up'
                    },
                    {
                        name: '/argon/index.html',
                        rank: '319',
                        salesPoint: '3,985',
                        bounceRate: '46,53%',
                        bounceRateDirection: 'down'
                    },
                    {
                        name: '/argon/charts.html',
                        rank: '294',
                        salesPoint: '3,513',
                        bounceRate: '36,49%',
                        bounceRateDirection: 'down'
                    },
                    {
                        name: '/argon/tables.html',
                        rank: '147',
                        salesPoint: '2,050',
                        bounceRate: '50,87%',
                        bounceRateDirection: 'up'
                    },
                    {
                        name: '/argon/profile.html',
                        rank: '190',
                        salesPoint: '1,795',
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
