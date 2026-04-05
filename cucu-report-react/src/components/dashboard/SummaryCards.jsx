import { Grid, Card, CardContent, Typography, Box } from "@mui/material";

export default function SummaryCards({ summary }) {
  const cards = [
    { label: "Total", value: summary.total, color: "#636e72" },
    { label: "Passed", value: summary.passed, color: "#00b894" },
    { label: "Failed", value: summary.failed, color: "#d63031" },
  ];

  return (
    <Grid container spacing={3} sx={{ mb: 3 }}>
      {cards.map((card) => (
        <Grid item xs={12} sm={4} key={card.label}>
          <Card
            sx={{
              borderLeft: `6px solid ${card.color}`,
              borderRadius: 2,
              boxShadow: 3,
            }}
          >
            <CardContent>
              <Box>
                <Typography variant="subtitle2" color="text.secondary">
                  {card.label}
                </Typography>

                <Typography
                  variant="h4"
                  sx={{ fontWeight: "bold", color: card.color }}
                >
                  {card.value}
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
}
