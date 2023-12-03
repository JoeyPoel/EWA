export const data = {
    labels: [
        'January',
        'February',
        'March',
        'April',
        'May',
        'June',
        'July',
        'August',
        'September',
        'October',
        'November',
        'December'
    ],
    datasets: [
        {
            label: 'Data One',
            backgroundColor: 'red',
            data: [40, 20, 12, 39, 10, 40, 39, 80, 40, 20, 12, 11]
        },
        {
            label: 'Data Two',
            backgroundColor: 'blue',
            data: [32, 23, 43, 51, 34, 54, 54, 51, 43, 54, 64, 65]
        },
        {
            label: 'Data Three',
            backgroundColor: 'green',
            data: [12, 34, 12, 15, 12, 34, 12, 15, 12, 34, 12, 15]
        }
    ]
}

export const options = {
    responsive: true,
    maintainAspectRatio: false
}