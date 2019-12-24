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
                    <th>순위 증감</th>
                    <th>판매지수</th>
                    <th>판매지수 증감률</th>
                </template>

                <template slot-scope="{row}">
                    <th scope="row">
                        {{row.name}}
                    </th>
                    <td>
                        {{row.rank}}
                    </td>
                    <td>
                        <i :class="row.bounceRankDirection === 'UP' ? 'text-success fa-arrow-up': 'text-primary fa-arrow-down'"
                           class="fas mr-3"></i>
                        {{row.bounceRank}}
                    </td>
                    <td>
                        {{row.salesPoint}}
                    </td>
                    <td>
                        <i :class="row.bounceRateDirection === 'UP' ? 'text-success fa-arrow-up': 'text-primary fa-arrow-down'"
                           class="fas mr-3"></i>
                        {{row.bounceRate}}
                    </td>
                </template>

            </base-table>
        </div>

    </div>
</template>
<script>
    import {fetchBookStores} from '../../api/index'

    export default {
        name: 'book-store-score-table',
        data() {
            return {
                title: '스프링 부트와 AWS로 혼자 구현하는 웹 서비스',
                bookStores: []
            }
        },
        async created() {
            const {data} = await fetchBookStores(1);
            this.bookStores = data.data
        }
    }
</script>
<style>
</style>
