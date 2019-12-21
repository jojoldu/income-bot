<template>
    <div>
        <base-header class="pb-6 pb-8 pt-5 pt-md-8" type="gradient-success">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-active-40"
                                sub-title="350,897"
                                title="Total traffic"
                                type="gradient-red"
                    >

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-chart-pie-35"
                                sub-title="2,356"
                                title="Total traffic"
                                type="gradient-orange"
                    >

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 12.18%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-money-coins"
                                sub-title="924"
                                title="Sales"
                                type="gradient-green"
                    >

                        <template slot="footer">
                            <span class="text-danger mr-2"><i class="fa fa-arrow-down"></i> 5.72%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>

                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-chart-bar-32"
                                sub-title="49,65%"
                                title="Performance"
                                type="gradient-info"
                    >

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 54.8%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>
            </div>
        </base-header>

        <!--Charts-->
        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col-xl-8 mb-5 mb-xl-0">
                    <card header-classes="bg-transparent" type="default">
                        <div class="row align-items-center" slot="header">
                            <div class="col">
                                <h6 class="text-light text-uppercase ls-1 mb-1">Overview</h6>
                                <h5 class="h3 text-white mb-0">Sales value</h5>
                            </div>
                            <div class="col">
                                <ul class="nav nav-pills justify-content-end">
                                    <li class="nav-item mr-2 mr-md-0">
                                        <a :class="{active: bigLineChart.activeIndex === 0}"
                                           @click.prevent="initBigChart(0)"
                                           class="nav-link py-2 px-3"
                                           href="#">
                                            <span class="d-none d-md-block">Month</span>
                                            <span class="d-md-none">M</span>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a :class="{active: bigLineChart.activeIndex === 1}"
                                           @click.prevent="initBigChart(1)"
                                           class="nav-link py-2 px-3"
                                           href="#">
                                            <span class="d-none d-md-block">Week</span>
                                            <span class="d-md-none">W</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <line-chart
                                :chart-data="bigLineChart.chartData"
                                :extra-options="bigLineChart.extraOptions"
                                :height="350"
                                ref="bigChart"
                        >
                        </line-chart>

                    </card>
                </div>

                <div class="col-xl-4">
                    <card header-classes="bg-transparent">
                        <div class="row align-items-center" slot="header">
                            <div class="col">
                                <h6 class="text-uppercase text-muted ls-1 mb-1">Performance</h6>
                                <h5 class="h3 mb-0">Total orders</h5>
                            </div>
                        </div>

                        <bar-chart
                                :chart-data="redBarChart.chartData"
                                :height="350"
                                ref="barChart"
                        >
                        </bar-chart>
                    </card>
                </div>
            </div>
            <!-- End charts-->

            <!--Tables-->
            <div class="row mt-5">
                <div class="col-xl-8 mb-5 mb-xl-0">
                    <page-visits-table></page-visits-table>
                </div>
                <div class="col-xl-4">
                    <social-traffic-table></social-traffic-table>
                </div>
            </div>
            <!--End tables-->
        </div>

    </div>
</template>
<script>
    // Charts
    import * as chartConfigs from '@/components/Charts/config';
    import LineChart from '@/components/Charts/LineChart';
    import BarChart from '@/components/Charts/BarChart';
    // Tables
    import SocialTrafficTable from './Dashboard/SocialTrafficTable';
    import PageVisitsTable from './Dashboard/BookStoreTodayView';

    export default {
        components: {
            LineChart,
            BarChart,
            PageVisitsTable,
            SocialTrafficTable,
        },
        data() {
            return {
                bigLineChart: {
                    allData: [
                        [0, 20, 10, 30, 15, 40, 20, 60, 60],
                        [0, 20, 5, 25, 10, 30, 15, 40, 40]
                    ],
                    activeIndex: 0,
                    chartData: {
                        datasets: [],
                        labels: [],
                    },
                    extraOptions: chartConfigs.blueChartOptions,
                },
                redBarChart: {
                    chartData: {
                        labels: ['Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                        datasets: [{
                            label: 'Sales',
                            data: [25, 20, 30, 22, 17, 29]
                        }]
                    }
                }
            };
        },
        methods: {
            initBigChart(index) {
                let chartData = {
                    datasets: [
                        {
                            label: 'Performance',
                            data: this.bigLineChart.allData[index]
                        }
                    ],
                    labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                };
                this.bigLineChart.chartData = chartData;
                this.bigLineChart.activeIndex = index;
            }
        },
        mounted() {
            this.initBigChart(0);
        }
    };
</script>
<style></style>
