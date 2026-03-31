const KycStatusBadge = ({status}) => {
    const styles = {
        PENDING: 'bg-yellow-100 text-yellow-800',
        UNDER_REVIEW:'bg-blue-100 text-blue-800',
        APPROVED:'bg-green-100 text-green-800',
        REJECTED:'bg-red-100 text-red-800',
        EXPIRED:'bg-gray-100 text-gray-800',
    };
    return (
        <span className={`px-3 py-1 rounded-full text-sm font-medium 
      ${styles[status] || styles.PENDING}`}>
            {status}
        </span>
    );
};
export default KycStatusBadge;